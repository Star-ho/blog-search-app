package com.blog.task.domain.blogSearch

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import java.lang.IllegalArgumentException

class SearchRequestUnitTest:DescribeSpec({
    describe("SearchRequest 생성 validation 테스트"){
        it("정상 생성"){
            SearchRequest("test",SearchRequest.Sort.accuracy, page = 10, size = 10)
        }
        context("에러 발생"){
            it("page가 1보다 작을떄"){
                shouldThrow<IllegalArgumentException> {
                    SearchRequest("test",SearchRequest.Sort.accuracy, page = 0, size = 10)
                }
            }
            it("page가 100보다 클떄"){
                shouldThrow<IllegalArgumentException> {
                    SearchRequest("test",SearchRequest.Sort.accuracy, page = 101, size = 10)
                }
            }
            it("size가 1보다 작을떄"){
                shouldThrow<IllegalArgumentException> {
                    SearchRequest("test",SearchRequest.Sort.accuracy, page = 10, size = 0)
                }
            }
            it("size가 100보다 클떄"){
                shouldThrow<IllegalArgumentException> {
                    SearchRequest("test",SearchRequest.Sort.accuracy, page = 10, size = 101)
                }
            }
        }
    }
})