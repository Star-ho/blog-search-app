package com.blog.task.blogSearch

import com.blog.task.domain.blogSearch.BlogSearch
import com.blog.task.domain.searchKeywordLog.SearchKeywordLog
import com.blog.task.domain.searchKeywordLog.SearchKeywordLogRepository
import com.blog.task.domain.blogSearch.BlogSearchResponse
import com.blog.task.domain.blogSearch.BlogSearchService
import com.blog.task.domain.blogSearch.SearchRequest
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDate

class BlogSearchServiceUnitTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val kakaoBlogSearch: BlogSearch = mockk()
    val naverBlogSearch: BlogSearch = mockk()
    val searchKeywordLogRepository: SearchKeywordLogRepository = mockk()

    val blogSearchService = BlogSearchService(kakaoBlogSearch, naverBlogSearch, searchKeywordLogRepository)

    describe("블로그 검색 테스트 "){

        val searchRequest = SearchRequest("query", SearchRequest.Sort.accuracy,1,10)
        val blogSearchResponse = BlogSearchResponse(
                BlogSearchResponse.Meta(10,1,10),
                listOf(BlogSearchResponse.Document("title","contents","url","blog-name", LocalDate.now()))
        )
        every { searchKeywordLogRepository.saveWithNoThrow(any()) } returns Unit

        it("카카오 블로그 검색 테스트"){
            val searchRequestSlot = slot<SearchRequest>()
            every { kakaoBlogSearch.getBlogData(capture(searchRequestSlot)) }returns blogSearchResponse

            blogSearchService.getData(searchRequest)

            verify(exactly = 1) { kakaoBlogSearch.getBlogData(capture(searchRequestSlot)) }
            verify(exactly = 0) { naverBlogSearch.getBlogData(any()) }
            val capturedRequest = searchRequestSlot.captured
            capturedRequest.let {
                it.query shouldBe searchRequest.query
                it.page shouldBe searchRequest.page
                it.size shouldBe searchRequest.size
                it.sort shouldBe searchRequest.sort
            }
        }

        it("카카오 검색 실패시 네이버 블로그 검색 테스트"){
            val searchRequestSlot = slot<SearchRequest>()
            every { kakaoBlogSearch.getBlogData(any()) } throws IllegalArgumentException()
            every { naverBlogSearch.getBlogData(capture(searchRequestSlot)) } returns blogSearchResponse

            blogSearchService.getData(searchRequest)

            verify(exactly = 1) { kakaoBlogSearch.getBlogData(any()) }
            verify(exactly = 1) { naverBlogSearch.getBlogData(capture(searchRequestSlot)) }

            val capturedRequest = searchRequestSlot.captured
            capturedRequest.let {
                it.query shouldBe searchRequest.query
                it.page shouldBe searchRequest.page
                it.size shouldBe searchRequest.size
                it.sort shouldBe searchRequest.sort
            }
        }

        it("검색 로그 저장 테스트"){
            val searchKeywordLogSlot = slot<SearchKeywordLog>()
            every { kakaoBlogSearch.getBlogData(any()) } returns blogSearchResponse
            every { searchKeywordLogRepository.saveWithNoThrow(capture(searchKeywordLogSlot)) } returns Unit

            blogSearchService.getData(searchRequest)


            verify(exactly = 1) { searchKeywordLogRepository.saveWithNoThrow(capture(searchKeywordLogSlot)) }
            val capturedRequest = searchKeywordLogSlot.captured
            capturedRequest.keyword.value shouldBe  searchRequest.query
        }

    }
})
