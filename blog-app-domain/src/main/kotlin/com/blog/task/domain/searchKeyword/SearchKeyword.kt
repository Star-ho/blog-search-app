package com.blog.task.domain.searchKeyword

import java.math.BigDecimal

class SearchKeyword(
        val id:Long = 0,
        keyword: String,
        hitCount:BigDecimal,
){
    val keyword:Keyword = Keyword(keyword)
    val hitCount:BigDecimal

    init {
        if (hitCount < BigDecimal.ZERO) throw IllegalArgumentException("검색 횟수는 0보다 작을 수 없습니다")
        this.hitCount = hitCount
    }
}

class Keyword(keyword:String){
    val value:String
    init {
        val value = keyword.trim()
        if(value.isEmpty())
            throw IllegalArgumentException("검색 키워드는 공백일 수 없습니다")
        if(value.length > 255)
            throw IllegalArgumentException("검색 키워드는 255자보다 많을 수 없습니다")
        this.value = value
    }
}