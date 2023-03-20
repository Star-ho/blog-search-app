package com.kakao.task.data.h2.searchKeyword

import com.kakao.task.domain.searchKeyword.SearchKeyword
import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import jakarta.persistence.EntityNotFoundException
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
        val updateRowNum = searchKeywordJpaRepository.updateHitCountByKeyword(keyword,addedHitCount)
        if(updateRowNum != 1) throw EntityNotFoundException("검색 키워드가 존재하지 않습니다")
    }

    override fun save(searchKeyword: SearchKeyword){
        searchKeywordJpaRepository.save(SearchKeywordDao.of(searchKeyword))
    }
}