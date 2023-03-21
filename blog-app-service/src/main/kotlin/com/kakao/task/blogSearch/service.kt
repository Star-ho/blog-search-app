package com.kakao.task.blogSearch

import com.kakao.task.domain.blogSearch.BlogSearch
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLogRepository
import com.kakao.task.domain.blogSearch.BlogSearchResponse
import com.kakao.task.domain.blogSearch.SearchRequest
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
        private val kakaoBlogSearch: BlogSearch,
        private val naverBlogSearch: BlogSearch,
        private val searchKeywordLogRepository: SearchKeywordLogRepository,
) {
    fun getData(searchRequest: SearchRequest): BlogSearchResponse? {
        val res = try {
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