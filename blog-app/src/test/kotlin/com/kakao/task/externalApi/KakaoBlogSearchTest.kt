package com.kakao.task.externalApi

import com.kakao.task.domain.blogSearch.SearchRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain

class KakaoBlogSearchTest: DescribeSpec({
    val kakaoBlogSearch = KakaoBlogSearch()

    describe("카카오 블로그 검색 요청 테스트"){
        context("요청한 페이지 갯수만큼 리턴한다"){
            it("10개 페이지 요청"){
                val request = SearchRequest("test", SearchRequest.Sort.accuracy,1,10)
                val res = kakaoBlogSearch.getBlogData(request)
                res?.documents?.size shouldBe 10
            }

            it("20개 페이지 요청"){
                val request = SearchRequest("test", SearchRequest.Sort.accuracy,1,20)
                val res = kakaoBlogSearch.getBlogData(request)
                res?.documents?.size shouldBe 20
            }

        }

        context("페이지네이션 테스트"){
            it("페이지간 요청이 다르다"){
                val firstRequest = SearchRequest("test", SearchRequest.Sort.accuracy, 1, 10)
                val firstRes = kakaoBlogSearch.getBlogData(firstRequest)

                val secondRequest = SearchRequest("test", SearchRequest.Sort.accuracy,2,10)
                val secondRes = kakaoBlogSearch.getBlogData(secondRequest)

                firstRes?.documents?.get(0) shouldNotBe null
                firstRes?.documents?.get(0)?.title shouldNotBe  secondRes?.documents?.get(0)?.title
            }

            it("size를 다르게하여 같은 블로그 호출"){
                val firstRequest = SearchRequest("test", SearchRequest.Sort.accuracy, 1, 10)
                val firstRes = kakaoBlogSearch.getBlogData(firstRequest)

                val secondRequest = SearchRequest("test", SearchRequest.Sort.accuracy,2,9)
                val secondRes = kakaoBlogSearch.getBlogData(secondRequest)

                firstRes?.documents?.get(9) shouldNotBe null
                firstRes?.documents?.get(9)?.title shouldBe   secondRes?.documents?.get(0)?.title
            }
        }

        context("x를 검색하면 x를 리턴한다"){
            it("1 검색") {
                val request = SearchRequest("1", SearchRequest.Sort.accuracy, 1, 10)
                val res = kakaoBlogSearch.getBlogData(request)
                res?.documents?.get(0)?.contents shouldContain "1"
            }
            it("2 검색"){
                val request = SearchRequest("2", SearchRequest.Sort.accuracy,2,10)
                val res = kakaoBlogSearch.getBlogData(request)
                res?.documents?.get(0)?.contents shouldContain "2"
            }
        }
    }
})