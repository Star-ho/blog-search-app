package com.kakao.task.domain.searchKeywordLog

interface SearchKeywordLogRepository{
    fun findByGraterThanId(id:Long): List<SearchKeywordLog>
    fun save(searchKeywordLog: SearchKeywordLog)
}