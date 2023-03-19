package com.kakao.task.domain.searchKeyword

import java.math.BigDecimal

interface SearchKeywordRepository{
    fun findByTop10SearchKeyword(): List<SearchKeyword>

    fun findByKeyword(keyword:String): SearchKeyword?

    fun updateSearchKeywordHitCount(keyword: String,addedHitCount: BigDecimal)

    fun save(searchKeyword: SearchKeyword)
}