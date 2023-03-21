package com.kakao.task.scheduler

import com.kakao.task.domain.searchKeyword.SearchKeyword
import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLogRepository
import com.kakao.task.domain.sequence.SequenceRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.math.BigDecimal

class SearchKeywordSchedulerUnitTest : DescribeSpec({
    val sequenceRepository: SequenceRepository = mockk()
    val searchKeywordLogRepository: SearchKeywordLogRepository = mockk()
    val searchKeywordRepository: SearchKeywordRepository = mockk()
    val searchKeywordScheduler = SearchKeywordScheduler(sequenceRepository, searchKeywordLogRepository, searchKeywordRepository)

    val searchKeyword1 = SearchKeyword(id = 1L, keyword = "keyword", hitCount = BigDecimal.ONE)
    val searchKeyword2 = SearchKeyword(id = 2L, keyword = "keyword2", hitCount = BigDecimal.TWO)
    val searchKeywordLogList = listOf(
            SearchKeywordLog(id = 11, keyword = "keyword"),
            SearchKeywordLog(id = 13, keyword = "keyword2"),
            SearchKeywordLog(id = 14, keyword = "keyword2"),
            SearchKeywordLog(id = 14, keyword = "keyword2"),
            SearchKeywordLog(id = 15, keyword = "keyword3"),
            SearchKeywordLog(id = 15, keyword = "keyword3"),
    )

    describe("검색어 조회수 업데이트 테스트") {
        val lastIndex = 10L
        val saveSearchKeywordSlot = slot<SearchKeyword>()
        val lastUpdateLogSequenceSlot = slot<Long>()
        val updateKeywordIdSlot = slot<Long>()
        val updateKeywordHiCountSlot = slot<BigDecimal>()
        every { sequenceRepository.findLastUpdateSearchSequence() } returns lastIndex
        every { searchKeywordRepository.findByKeyword(searchKeyword1.keyword.value) } returns searchKeyword1
        every { searchKeywordRepository.findByKeyword(searchKeyword2.keyword.value) } returns searchKeyword2
        every { searchKeywordRepository.findByKeyword("keyword3") } returns null
        every { searchKeywordLogRepository.findByGraterThanId(lastIndex) } returns searchKeywordLogList
        every { searchKeywordRepository.updateSearchKeywordHitCount(capture(updateKeywordIdSlot),
                capture(updateKeywordHiCountSlot)) } returns Unit
        every { searchKeywordRepository.save(capture(saveSearchKeywordSlot)) } returns Unit
        every { sequenceRepository.saveLastUpdateLogSequence(capture(lastUpdateLogSequenceSlot)) } returns Unit

        searchKeywordScheduler.updateSearchKeywordHitCount()

        it("메서드 호출 확인") {

            verify(exactly = 1) { sequenceRepository.findLastUpdateSearchSequence() }
            verify(exactly = 1) { searchKeywordLogRepository.findByGraterThanId(lastIndex) }
            verify(exactly = 2) { searchKeywordRepository.updateSearchKeywordHitCount(capture(updateKeywordIdSlot),
                    capture(updateKeywordHiCountSlot)) }

            verify(exactly = 1) { sequenceRepository.saveLastUpdateLogSequence(any()) }
            verify(exactly = 1) { searchKeywordRepository.save(capture(saveSearchKeywordSlot)) }
            verify(exactly = 1) { searchKeywordRepository.findByKeyword(searchKeyword1.keyword.value) }
            verify(exactly = 1) { searchKeywordRepository.findByKeyword(searchKeyword1.keyword.value) }
        }

        it("저장 및 변수 업데이트 파라미터 확인") {
            updateKeywordIdSlot.captured shouldBe 2L
            updateKeywordHiCountSlot.captured.compareTo(BigDecimal(3)) shouldBe 0
            lastUpdateLogSequenceSlot.captured shouldBe 15
            saveSearchKeywordSlot.captured.keyword.value shouldBe "keyword3"
            saveSearchKeywordSlot.captured.hitCount.compareTo(BigDecimal.TWO) shouldBe 0
        }
    }
})

