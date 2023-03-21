package com.blog.task.domain.searchKeywordLog

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException
import java.time.ZonedDateTime

class SearchKeywordLogTest : DescribeSpec({
    describe("객체 생성 테스트"){
        it("생성"){
            val id = 0L
            val keyword = "keyword1"
            val createdAt = ZonedDateTime.now()
            val searchKeywordLog = SearchKeywordLog(id,keyword,createdAt)

            searchKeywordLog.id shouldBe id
            searchKeywordLog.keyword.value shouldBe keyword
            searchKeywordLog.createdAt shouldBe createdAt
        }

        it("키워드의 길이가 0이면 에러"){
            val id = 0L
            val keyword = ""
            shouldThrow<IllegalArgumentException> {
                SearchKeywordLog(id,keyword)
            }
        }

        it("키워드의 길이가 공백이면 에러"){
            val id = 0L
            val keyword = "   "
            shouldThrow<IllegalArgumentException> {
                SearchKeywordLog(id,keyword)
            }
        }
    }

})
