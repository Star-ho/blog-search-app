package com.blog.task.domain.blogSearch

import com.blog.task.domain.searchKeywordLog.SearchKeywordLog
import com.blog.task.domain.searchKeywordLog.SearchKeywordLogRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

class BlogSearchService(
        private val kakaoBlogSearch: BlogSearch,
        private val naverBlogSearch: BlogSearch,
        private val searchKeywordLogRepository: SearchKeywordLogRepository,
) {
    fun getData(searchRequest: SearchRequest): BlogSearchResponse? {
        val res = runBlocking {
            select{
                async{ naverBlogSearch.getBlogData(searchRequest) }.onAwait{ it }
                async { kakaoBlogSearch.getBlogData(searchRequest) }.onAwait{ it }
            }.also { this.coroutineContext.cancelChildren() }
        }

        searchKeywordLogRepository.saveWithNoThrow(SearchKeywordLog(keyword = searchRequest.query))

        return res
    }
}