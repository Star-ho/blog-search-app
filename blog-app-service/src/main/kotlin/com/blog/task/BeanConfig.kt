package com.blog.task

import com.blog.task.domain.blogSearch.BlogSearch
import com.blog.task.domain.blogSearch.BlogSearchService
import com.blog.task.domain.popularKeyword.PopularKeywordService
import com.blog.task.domain.searchKeyword.SearchKeywordRepository
import com.blog.task.domain.searchKeywordLog.SearchKeywordLogRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun blogSearchService(kakaoBlogSearch: BlogSearch,
                          naverBlogSearch: BlogSearch,
                          searchKeywordLogRepository: SearchKeywordLogRepository,): BlogSearchService {
        return BlogSearchService(kakaoBlogSearch, naverBlogSearch, searchKeywordLogRepository)
    }

    @Bean
    fun popularKeywordService(searchKeywordRepository: SearchKeywordRepository): PopularKeywordService {
        return PopularKeywordService(searchKeywordRepository)
    }

}