package com.blog.task.externalApi

import com.blog.task.domain.blogSearch.SearchRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.net.URI

class KakaoBlogSearchUnitTest : DescribeSpec({
    val blogWebClient: BlogWebClient = mockk()
    val kakaoBlogSearch = KakaoBlogSearch("test Auth", blogWebClient)

    describe("카카오 블로그 검색 파라미터 테스트") {
        val searchRequest = SearchRequest("test", SearchRequest.Sort.recency, 3, 20)
        val uriSlot = slot<URI>()
        val response = KakaoBlogSearchResponse(
            KakaoBlogSearchResponse.Meta(100, 10, false),
            listOf()
        )
        every { blogWebClient.get(capture(uriSlot), any(), KakaoBlogSearchResponse::class.java) } returns response

        val res = kakaoBlogSearch.getBlogData(searchRequest)

        it("요청 파라미터 테스트") {
            uriSlot.captured.query shouldBe "query=test&sort=recency&page=3&size=20"
        }

        it("응답 파라미터 테스트") {
            res shouldNotBe null
            res?.meta?.start shouldBe 41
            res?.meta?.size shouldBe 20
            res?.meta?.total shouldBe 10
        }
    }
})
