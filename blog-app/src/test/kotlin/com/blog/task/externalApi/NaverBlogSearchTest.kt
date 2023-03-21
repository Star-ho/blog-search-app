package com.blog.task.externalApi

import com.blog.task.domain.blogSearch.SearchRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class NaverBlogSearchTest:DescribeSpec({
    val naverBlogSearch = NaverBlogSearch()

    describe("네이버 블로그 검색 요청 테스트"){
        context("요청한 페이지 갯수만큼 리턴한다"){
            it("10개 페이지 요청"){
                val request = SearchRequest("test", SearchRequest.Sort.accuracy,1,10)
                val res = naverBlogSearch.getBlogData(request)
                res?.documents?.size shouldBe 10
            }

            it("20개 페이지 요청"){
                val request = SearchRequest("test", SearchRequest.Sort.accuracy,1,20)
                val res = naverBlogSearch.getBlogData(request)
                res?.documents?.size shouldBe 20
            }

        }

        context("n번째 페이지를 요청하면 n번째 페이지를 리턴한다"){
            it("첫번째 페이지 요청") {
                val request = SearchRequest("test", SearchRequest.Sort.accuracy, 1, 10)
                val res = naverBlogSearch.getBlogData(request)
                res?.meta?.start shouldBe 1
            }
            it("두번째 페이지 요청"){
                val request = SearchRequest("test", SearchRequest.Sort.accuracy,2,10)
                val res = naverBlogSearch.getBlogData(request)
                res?.meta?.start shouldBe 11
            }
        }

        context("x를 검색하면 x를 리턴한다"){
            it("1 검색") {
                val request = SearchRequest("1", SearchRequest.Sort.accuracy, 1, 10)
                val res = naverBlogSearch.getBlogData(request)
                res?.documents?.get(0)?.contents shouldContain "1"
            }
            it("2 검색"){
                val request = SearchRequest("2", SearchRequest.Sort.accuracy,2,10)
                val res = naverBlogSearch.getBlogData(request)
                res?.documents?.get(0)?.contents shouldContain "2"
            }
        }
    }
})