package com.kakao.task.domain.searchKeyword

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.math.BigDecimal

class SearchKeywordTest:DescribeSpec({
    describe("객체 생성 테스트"){
        it("생성"){
            val id = 0L
            val keyword = "keyword1"
            val hitCount = BigDecimal.ONE
            val searchKeyword = SearchKeyword(id,keyword, hitCount)

            searchKeyword.id shouldBe id
            searchKeyword.keyword.value shouldBe keyword
            searchKeyword.hitCount shouldBe  hitCount
        }

        context("hitCount가 0미만 테스트"){
            it("0이면 에러 발생하지 않음"){
                val id = 0L
                val keyword = "keyword1"
                val hitCount = BigDecimal(0)
                shouldNotThrow<IllegalArgumentException> {
                    SearchKeyword(id,keyword, hitCount)
                }
            }

            it("0미만일시 에러"){
                val id = 0L
                val keyword = "keyword1"
                val hitCount = BigDecimal(-1)
                shouldThrow<IllegalArgumentException> {
                    SearchKeyword(id,keyword, hitCount)
                }
            }
        }
    }
})

class KeywordTest:DescribeSpec({
    describe("객체 생성 테스트"){
        it("생성 테스트"){
            val value = "test"
            val keyword = Keyword(value)
            keyword.value shouldBe value
        }

        it("키워드가 공백이면 에러"){
            val value = "    "
            shouldThrow<IllegalArgumentException> {
                Keyword(value)
            }
        }
        context("키워드 길이가 0일때 에러"){
            it("1일때는 에러 나지 않음"){
                val value = "1"
                shouldNotThrow<IllegalArgumentException> {
                    Keyword(value)
                }
            }

            it("키워드의 길이가 0이면 에러"){
                val value = ""
                shouldThrow<IllegalArgumentException> {
                    Keyword(value)
                }
            }
        }

        context("키워드가 길때 에러"){
            it("키워드가 255자일때 생성 가능"){
                val value = StringBuilder("a").repeat(255)

                shouldNotThrow<IllegalArgumentException> {
                    Keyword(value)
                }
            }

            it("키워드가 256자일때 에러"){
                val value = StringBuilder("a").repeat(256)

                shouldThrow<IllegalArgumentException> {
                    Keyword(value)
                }
            }
        }
    }
})