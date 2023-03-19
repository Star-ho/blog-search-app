package com.kakao.task.externalApi

import kotlinx.serialization.Serializable
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

private const val NAVER_API_HOST = "openapi.naver.com"
private const val NAVER_BLOG_SEARCH_API_PATH = "/v1/search/blog"
private const val NAVER_CLIENT_ID = "8sxE0aH9L3TpiYWbD1F1"
private const val NAVER_CLIENT_SECRET = "sxqThuV909"
@Component
class NaverBlogSearch: BlogSearch {
    override fun getBlogData(searchRequest: SearchRequest): BlogSearchResponse? {
        val naverSearchRequest = searchRequest.toNaverSearchRequest()
        val webClient = WebClient.create()

        return webClient.get()
                .uri{
                    it.scheme("https")
                            .host(NAVER_API_HOST)
                            .path(NAVER_BLOG_SEARCH_API_PATH)
                            .queryParam("query",naverSearchRequest.query)
                            .queryParam("display",naverSearchRequest.display)
                            .queryParam("start",naverSearchRequest.start)
                            .queryParam("sort",naverSearchRequest.sort.value)
                            .build()
                }
                .headers {
                    it.set("X-Naver-Client-Id", NAVER_CLIENT_ID)
                    it.set("X-Naver-Client-Secret", NAVER_CLIENT_SECRET)
                    it.contentType = MediaType.APPLICATION_JSON
                }.retrieve()
                .bodyToMono(NaverBlogSearchResponse::class.java)
                .block()?.toBlogSearchResponse()
    }
}

private fun SearchRequest.toNaverSearchRequest(): NaverBlogSearchRequest {
    return NaverBlogSearchRequest(query, size, page, NaverBlogSearchRequest.Sort.of(this.sort.name))
}
@Serializable
class NaverBlogSearchRequest(
        val query:String,
        val display:Int,
        val start:Int,
        val sort: Sort,
){
    enum class Sort(val value:String,val matchValue:String){
        SIM("sim","accuracy"),DATE("date","recency");
        companion object{
            fun of(value: String): Sort {
                Sort.values().forEach {
                    if(it.matchValue == value){
                        return it
                    }
                }
                throw ClassNotFoundException("NaverBlogSearchRequest sort Enum을 찾을 수 없습니다")
            }
        }
    }
}

@Serializable
class NaverBlogSearchResponse(
        val lastBuildDate:String,
        val total:Int,
        val start:Int,
        val display:Int,
        val items:List<Item>
){
    @Serializable
    class Item(
            val title:String,
            val link: String,
            val description:String,
            val bloggername:String,
            val bloggerlink:String,
            val postdate: String,
    )
    fun toBlogSearchResponse(): BlogSearchResponse {
        //TODO: 2023/03/19 매핑 수정할것  - 성호
        return BlogSearchResponse(
                BlogSearchResponse.Meta(total,start/display,total <= (start*display+display)),
                items.map { it.run { BlogSearchResponse.Document(title,description,link,title,"thumbname",postdate) } }
        )
    }
}