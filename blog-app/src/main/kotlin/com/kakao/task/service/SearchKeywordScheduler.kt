package com.kakao.task.searchKeyword

import com.kakao.task.domain.sequence.SequenceRepository
import com.kakao.task.domain.searchKeyword.SearchKeyword
import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLogRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class SearchKeywordScheduler(
        private val sequenceRepository: SequenceRepository,
        private val searchKeywordLogRepository: SearchKeywordLogRepository,
        private val searchKeywordRepository: SearchKeywordRepository,
) {
    @Transactional
    @Scheduled(fixedDelay = 1000L)
    fun updateSearchKeywordHitCount() {
        val lastUpdateLogSequence = sequenceRepository.findLastUpdateSearchSequence()
        val searchKeywordLogList = searchKeywordLogRepository.findByGraterThanId(lastUpdateLogSequence)
        if (searchKeywordLogList.isEmpty()) return

        val keywordHitCountMap = createKeywordHitCountMap(searchKeywordLogList)

        updateSearchKeywordHitCount(keywordHitCountMap)

        val lastLogSequence = searchKeywordLogList.maxBy { it.id }.id
        sequenceRepository.saveLastUpdateLogSequence(lastLogSequence)
    }

    private fun createKeywordHitCountMap(searchKeywordLogList:List<SearchKeywordLog>): Map<String, BigDecimal> {
        val keywordHitCountMap: MutableMap<String, BigDecimal> = mutableMapOf()
        searchKeywordLogList.forEach {
            if (!keywordHitCountMap.containsKey(it.keyword.value)) {
                keywordHitCountMap[it.keyword.value] = BigDecimal.ONE
                return@forEach
            }
            keywordHitCountMap[it.keyword.value] = keywordHitCountMap[it.keyword.value]!!.plus(BigDecimal.ONE)
        }
        return keywordHitCountMap
    }

    private fun updateSearchKeywordHitCount(keywordHitCountMap:Map<String, BigDecimal>){
        keywordHitCountMap.forEach {
            val searchKeyword = searchKeywordRepository.findByKeyword(it.key)
            if (searchKeyword == null) {
                searchKeywordRepository.save(SearchKeyword(keyword = it.key, hitCount = it.value))
                return@forEach
            }
            searchKeywordRepository.updateSearchKeywordHitCount(keyword = it.key, addedHitCount = it.value)
        }
    }

}