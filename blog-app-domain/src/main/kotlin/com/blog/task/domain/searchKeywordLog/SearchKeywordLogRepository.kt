package com.blog.task.domain.searchKeywordLog

interface SearchKeywordLogRepository{
    fun findByGraterThanId(id:Long): List<SearchKeywordLog>
    fun saveWithNoThrow(searchKeywordLog: SearchKeywordLog)
}