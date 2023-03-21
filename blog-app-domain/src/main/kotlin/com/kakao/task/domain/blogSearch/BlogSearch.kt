package com.kakao.task.domain.blogSearch

interface BlogSearch{
    fun getBlogData(searchRequest: SearchRequest): BlogSearchResponse?
}

class SearchRequest(
        val query:String,
        val sort: Sort = Sort.accuracy,
        //TODO: 2023/03/20 page가 0이하일때 밸리데이션  - 성호
        val page:Int = 1,
        val size:Int = 10,
){
    enum class Sort{
        accuracy,recency
    }
}
//TODO: 2023/03/20 리스폰스 매핑  - 성호
class BlogSearchResponse(
        val meta: Meta,
        val documents:List<Document>
){
    class Meta(
            //TODO: 2023/03/20 페이지 카운트 구할때 에러  - 성호
            val total:Int,
            val start:Int,
            val size:Int
    )
    class Document(
            val title:String,
            val contents:String,
            val url:String,
            val blogname:String,
            val datetime: String
    )
}
