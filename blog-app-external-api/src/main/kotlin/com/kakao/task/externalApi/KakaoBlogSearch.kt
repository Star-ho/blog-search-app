package com.kakao.task.externalApi

import kotlinx.serialization.Serializable
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

private const val KAKAKO_API_HOST= "dapi.kakao.com"
private const val KAKAO_BLOG_SEARCH_API_PATH = "/v2/search/blog"
private const val KAKAO_AUTH = "KakaoAK 3a81015bc9f0bfb464989a4da0af716f"
@Component
class KakaoBlogSearch: BlogSearch {
    override fun getBlogData(searchRequest: SearchRequest): BlogSearchResponse? {
        val kakaoSearchRequest = searchRequest.toKakaoSearchRequest()

        return WebClient.create().get()
                .uri{
                    it.scheme("https")
                            .host(KAKAKO_API_HOST)
                            .path(KAKAO_BLOG_SEARCH_API_PATH)
                            .queryParam("query",kakaoSearchRequest.query)
                            .queryParam("sort",kakaoSearchRequest.sort)
                            .queryParam("page",kakaoSearchRequest.page)
                            .queryParam("size",kakaoSearchRequest.size)
                            .build()
                }
                .headers {
                    it.set("Authorization", KAKAO_AUTH)
                    it.contentType = MediaType.APPLICATION_JSON
                }.retrieve()
                .bodyToMono(KakaoBlogSearchResponse::class.java)
                .block()?.toBlogSearchResponse()
    }
}

private fun SearchRequest.toKakaoSearchRequest(): KakaoSearchRequest {
    return KakaoSearchRequest(query, KakaoSearchRequest.Sort.of(this.sort.name),page, size)
}
@Serializable
class KakaoSearchRequest(
        val query:String,
        val sort: Sort,
        val page:Int,
        val size:Int,

        ) {
    enum class Sort(val value:String){
        accuracy("accuracy"),recency("recency");
        companion object{
            fun of(value: String): Sort {
                values().forEach {
                    if(it.value == value){
                        return it
                    }
                }
                throw IllegalArgumentException("해당 정렬 방식은 존재하지 않습니다")
            }
        }
    }
}

@Serializable
class KakaoBlogSearchResponse(
        val meta: Meta,
        val documents:List<Document>
){

    @Serializable
    class Meta(
            val total_count:Int,
            val pageable_count:Int,
            val is_end:Boolean
    )

    @Serializable
    class Document(
            val title:String,
            val contents:String,
            val url:String,
            val blogname:String,
            val thumbnail:String,
            val datetime:String
    )
    fun toBlogSearchResponse(): BlogSearchResponse {
        return BlogSearchResponse(
                this.meta.run { BlogSearchResponse.Meta(total_count, pageable_count, is_end) },
                this.documents.map { it.run { BlogSearchResponse.Document(title, contents, url, blogname, thumbnail, datetime) } }
        )
    }
}