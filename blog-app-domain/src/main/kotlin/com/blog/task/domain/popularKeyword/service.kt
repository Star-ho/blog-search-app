package com.blog.task.domain.popularKeyword

import com.blog.task.domain.searchKeyword.SearchKeywordRepository
import kotlinx.serialization.Serializable


class PopularKeywordService(
        private val searchKeywordRepository: SearchKeywordRepository
){
    fun getPopularSearchKeyword(): List<GetPopularSearchKeywordResponse> {
        return searchKeywordRepository.findByTop10SearchKeyword()
                .map { GetPopularSearchKeywordResponse(it.keyword.value,it.hitCount.toString()) }
    }
}

@Serializable
class GetPopularSearchKeywordResponse(
        val keyword:String,
        val hitCount:String,
)