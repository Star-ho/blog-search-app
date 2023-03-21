package com.kakao.task.domain.blogSearch

import java.lang.IllegalArgumentException
import java.time.LocalDate

interface BlogSearch{
    fun getBlogData(searchRequest: SearchRequest): BlogSearchResponse?
}

class SearchRequest(
        val query:String,
        val sort: Sort = Sort.accuracy,
        val page:Int = 1,
        val size:Int = 10,
){
    init {
        if(page < 1) throw IllegalArgumentException("0이하 페이지는 조회할 수 없습니다")
        if(page > 100) throw IllegalArgumentException("100번을 초과하는 페이지는 조회할 수 없습니다")
        if(size < 1) throw IllegalArgumentException("1개 미만 조회할 수 없습니다")
        if(size > 100) throw IllegalArgumentException("100개를 초과하여 조회할 수 없습니다")
    }
    enum class Sort{
        accuracy,recency
    }
}
class BlogSearchResponse(
        val meta: Meta,
        val documents:List<Document>
){
    class Meta(
            val total:Int,
            val start:Int,
            val size:Int
    )
    class Document(
            val title:String,
            val contents:String,
            val url:String,
            val blogname:String,
            val datetime: LocalDate
    )
}
