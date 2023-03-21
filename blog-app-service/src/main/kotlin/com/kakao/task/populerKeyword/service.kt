package com.kakao.task.populerKeyword

import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import kotlinx.serialization.Serializable
import org.springframework.stereotype.Component


@Component
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