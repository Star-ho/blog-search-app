package com.kakao.task.data.h2.searchKeyword

import com.kakao.task.domain.searchKeyword.SearchKeyword
import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class SearchKeywordRepositoryImpl(
    private val searchKeywordJpaRepository: SearchKeywordJpaRepository,
): SearchKeywordRepository {
    override fun findByTop10SearchKeyword(): List<SearchKeyword> {
        return searchKeywordJpaRepository.findByTop10SearchKeyword().map { it.toEntity() }
    }

    override fun findByKeyword(keyword:String): SearchKeyword? {
        return searchKeywordJpaRepository.findByKeyword(keyword)?.toEntity()
    }

    override fun updateSearchKeywordHitCount(keyword: String, addedHitCount:BigDecimal){
        searchKeywordJpaRepository.updateHitCountByKeyword(keyword,addedHitCount)
    }

    override fun save(searchKeyword: SearchKeyword){
        searchKeywordJpaRepository.save(SearchKeywordDao.of(searchKeyword))
    }
}