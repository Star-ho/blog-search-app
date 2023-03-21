package com.blog.task.blogSearch

import com.blog.task.domain.blogSearch.BlogSearch
import com.blog.task.domain.searchKeywordLog.SearchKeywordLog
import com.blog.task.domain.searchKeywordLog.SearchKeywordLogRepository
import com.blog.task.domain.blogSearch.BlogSearchResponse
import com.blog.task.domain.blogSearch.SearchRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
        private val kakaoBlogSearch: BlogSearch,
        private val naverBlogSearch: BlogSearch,
        private val searchKeywordLogRepository: SearchKeywordLogRepository,
) {
    fun getData(searchRequest: SearchRequest): BlogSearchResponse? {
        val res = try {
            if(searchRequest.size > 50 && searchRequest.page > 50) naverBlogSearch.getBlogData(searchRequest)
            kakaoBlogSearch.getBlogData(searchRequest)
        } catch (e: Exception) {
            naverBlogSearch.getBlogData(searchRequest)
        }

        try {
            searchKeywordLogRepository.save(SearchKeywordLog(keyword = searchRequest.query))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return res
    }
}