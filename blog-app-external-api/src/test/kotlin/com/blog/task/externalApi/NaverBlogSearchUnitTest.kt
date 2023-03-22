package com.blog.task.externalApi

import com.blog.task.domain.blogSearch.SearchRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.net.URI

class NaverBlogSearchUnitTest : DescribeSpec({
    val blogWebClient: BlogWebClient = mockk()
    val naverBlogSearch = NaverBlogSearch("testClientId", "testClientSecret", blogWebClient)

    describe("네이버 블로그 검색 파라미터 테스트") {
        val searchRequest = SearchRequest("test", SearchRequest.Sort.recency, 3, 20)
        val uriSlot = slot<URI>()
        val response = NaverBlogSearchResponse(
            lastBuildDate = "lastBuildDate", total = 100, start = 57, display = 20, items = listOf()
        )
        every { blogWebClient.get(capture(uriSlot), any(), NaverBlogSearchResponse::class.java) } returns response

        val res = naverBlogSearch.getBlogData(searchRequest)

        it("요청 파라미터 테스트") {
            uriSlot.captured.query shouldBe "query=test&display=20&start=41&sort=date"
        }

        it("응답 파라미터 테스트") {
            res shouldNotBe null
            res?.meta?.start shouldBe 57
            res?.meta?.size shouldBe 20
            res?.meta?.total shouldBe 100
        }
    }
})