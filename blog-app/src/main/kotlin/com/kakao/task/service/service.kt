package com.kakao.task.service

import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLogRepository
import com.kakao.task.externalApi.KakaoBlogSearch
import com.kakao.task.externalApi.SearchRequest
import com.kakao.task.externalApi.BlogSearchResponse
import com.kakao.task.externalApi.NaverBlogSearch
import kotlinx.serialization.Serializable
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class BlogSearchService(
        private val kakaoBlogSearch: KakaoBlogSearch,
        private val naverBlogSearch: NaverBlogSearch,
        private val searchKeywordLogRepository: SearchKeywordLogRepository,
) {
    //TODO: 2023/03/20 카카오 조회 실패시 네이버 조회 로직  - 성호
    // ++ 테스트
    fun getData(searchRequest: SearchRequest): BlogSearchResponse? {
        val res = try{ kakaoBlogSearch.getBlogData(searchRequest) }
                catch (e:Exception){naverBlogSearch.getBlogData(searchRequest)}

        try {
            searchKeywordLogRepository.save(SearchKeywordLog(keyword = searchRequest.query))
        }catch (e:Exception){
            e.printStackTrace()
        }

        return res
    }
}

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