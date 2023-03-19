package com.kakao.task.data.h2.searchKeywordLog

import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLogRepository
import org.springframework.stereotype.Repository

@Repository
class SearchKeywordLogRepositoryImpl(
        private val searchKeywordLogJpaRepository: SearchKeywordLogJpaRepository
):SearchKeywordLogRepository{
    override fun findByGraterThanId(id:Long): List<SearchKeywordLog> {
        return searchKeywordLogJpaRepository.findByGraterThanId(id).map { it.toEntity() }
    }
    override fun save(searchKeywordLog: SearchKeywordLog){
        searchKeywordLogJpaRepository.save(SearchKeywordLogDao.of(searchKeywordLog))
    }
}