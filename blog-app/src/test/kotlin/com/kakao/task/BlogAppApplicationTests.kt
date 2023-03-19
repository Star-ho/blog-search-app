package com.kakao.task

import com.kakao.task.externalApi.KakaoBlogSearch
import com.kakao.task.externalApi.SearchRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KakaoBlogSearchTest @Autowired constructor(
        private val kakaoBlogSearch: KakaoBlogSearch,
//        private val naverBlogSearch: NaverBlogSearch,
) {
    @Test
    fun getKakaoBlogData() {
        val req = SearchRequest(
                "test", SearchRequest.Sort.accuracy,10,10
        )
        kakaoBlogSearch.getBlogData(req)
    }

//    @Test
//    fun getNaverBlogData() {
//        val req = NaverSearchRequest(
//                "test", NaverSearchRequest.Sort.SIM,10,10
//        )
//
//        runBlocking {
//            naverBlogSearch.getBlogData(req)
//        }
//    }
}