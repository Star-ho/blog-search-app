package com.blog.task.data.h2.searchKeywordLog

import com.blog.task.domain.searchKeywordLog.SearchKeywordLog
import com.blog.task.domain.searchKeywordLog.SearchKeywordLogRepository
import org.springframework.stereotype.Repository

@Repository
class SearchKeywordLogRepositoryImpl(
        private val searchKeywordLogJpaRepository: SearchKeywordLogJpaRepository
):SearchKeywordLogRepository{
    override fun findByGraterThanId(id:Long): List<SearchKeywordLog> {
        return searchKeywordLogJpaRepository.findByGraterThanId(id).map { it.toEntity() }
    }
    override fun saveWithNoThrow(searchKeywordLog: SearchKeywordLog) {
        try {
            searchKeywordLogJpaRepository.save(SearchKeywordLogDao.of(searchKeywordLog))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}