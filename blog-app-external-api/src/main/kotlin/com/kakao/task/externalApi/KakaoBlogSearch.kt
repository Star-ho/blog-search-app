package com.kakao.task.externalApi

import com.kakao.task.domain.blogSearch.BlogSearch
import com.kakao.task.domain.blogSearch.BlogSearchResponse
import com.kakao.task.domain.blogSearch.SearchRequest
import kotlinx.serialization.Serializable
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.ZonedDateTime

//TODO: 2023/03/21 프로퍼티로 뺴기  - 성호
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
                .block()?.toBlogSearchResponse(calculateStartIndex(kakaoSearchRequest),kakaoSearchRequest.size)
    }

    fun calculateStartIndex(kakaoSearchRequest: KakaoSearchRequest): Int {
        return kakaoSearchRequest.page*(kakaoSearchRequest.size-1)
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

class KakaoBlogSearchResponse(
        val meta: Meta,
        val documents:List<Document>
){

    class Meta(
            val total_count:Int,
            val pageable_count:Int,
            val is_end:Boolean
    )

    class Document(
            val title:String,
            val contents:String,
            val url:String,
            val blogname:String,
            val thumbnail:String,
            val datetime:ZonedDateTime
    )
    fun toBlogSearchResponse(startIndex: Int, size: Int): BlogSearchResponse {
        return BlogSearchResponse(
                //  total_count도 있지만 실제로 조회할수 있는 수는 pageable_count기에 pageavle_count를 리턴함
                this.meta.run { BlogSearchResponse.Meta(pageable_count, startIndex, size) },
                this.documents.map { it.run { BlogSearchResponse.Document(title, contents, url, blogname, datetime.toLocalDate()) } }
        )
    }
}