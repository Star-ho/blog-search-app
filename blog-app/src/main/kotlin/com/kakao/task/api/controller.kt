package com.kakao.task.searchKeyword

import com.kakao.task.externalApi.SearchRequest
import com.kakao.task.externalApi.BlogSearchResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class BlogSearchController(
        private val blogSearchService: BlogSearchService
) {
    @GetMapping("/blog/search")
    fun searchBlog(searchRequest: SearchRequest): BlogSearchResponse? {
        return blogSearchService.getData(searchRequest)
    }

    @GetMapping("/popular-search-keyword")
    fun getPopularSearchKeyword(): List<GetPopularSearchKeywordResponse> {
        return blogSearchService.getPopularSearchKeyword()
    }
}

