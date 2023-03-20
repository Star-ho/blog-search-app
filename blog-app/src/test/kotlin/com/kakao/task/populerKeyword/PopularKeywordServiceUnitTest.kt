package com.kakao.task.populerKeyword

import com.kakao.task.domain.searchKeyword.SearchKeyword
import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.math.BigDecimal

class PopularKeywordServiceUnitTest : DescribeSpec({
    val searchKeywordRepository : SearchKeywordRepository = mockk()
    val popularKeywordService = PopularKeywordService(searchKeywordRepository)

    val searchKeywordList = listOf(
            SearchKeyword(keyword = "keyword", hitCount = BigDecimal.ONE),
            SearchKeyword(keyword = "keyword2", hitCount = BigDecimal.TWO),
    )

    describe("인기 검색어 조회 테스트") {
        it("findByTop10SearchKeyword 호출 확인"){
            every { searchKeywordRepository.findByTop10SearchKeyword() } returns searchKeywordList

            popularKeywordService.getPopularSearchKeyword()

            verify(exactly = 1){ searchKeywordRepository.findByTop10SearchKeyword() }
        }

        it("mapping 확인"){
            every { searchKeywordRepository.findByTop10SearchKeyword() } returns searchKeywordList

            val res = popularKeywordService.getPopularSearchKeyword()

            res.forEachIndexed { index, getPopularSearchKeywordResponse ->
                getPopularSearchKeywordResponse.keyword shouldBe searchKeywordList[index].keyword.value
                getPopularSearchKeywordResponse.hitCount shouldBe searchKeywordList[index].hitCount.toString()
            }
        }

    }
})
