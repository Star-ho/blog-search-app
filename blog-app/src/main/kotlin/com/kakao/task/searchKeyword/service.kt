package com.kakao.task.searchKeyword

import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import com.kakao.task.domain.searchKeywordLog.SearchKeywordLogRepository
import com.kakao.task.externalApi.KakaoBlogSearch
import com.kakao.task.externalApi.SearchRequest
import com.kakao.task.externalApi.BlogSearchResponse
import com.kakao.task.externalApi.NaverBlogSearch
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BlogSearchService(
        private val kakaoBlogSearch: KakaoBlogSearch,
        private val naverBlogSearch: NaverBlogSearch,
        private val searchKeywordLogRepository: SearchKeywordLogRepository,
        private val searchKeywordRepository: SearchKeywordRepository
) {
    //TODO: 2023/03/20 h2 스키마 만들기  - 성호
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

    fun getPopularSearchKeyword(): List<GetPopularSearchKeywordResponse> {
        return searchKeywordRepository.findByTop10SearchKeyword()
                .map { GetPopularSearchKeywordResponse(it.keyword.value,it.hitCount) }
    }
}

class GetPopularSearchKeywordResponse(
        val keyword:String,
        val hitCount:BigDecimal,
)