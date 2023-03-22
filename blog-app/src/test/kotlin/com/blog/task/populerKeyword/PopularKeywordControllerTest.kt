package com.blog.task.populerKeyword


import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PopularKeywordControllerTest : FunSpec({
    test("블로그 검색 api test"){
        WebClient.create().get()
            .uri("http://localhost:8080/popular-keyword")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(ResponseEntity::class.java)
            .subscribe{
                it.statusCode.is2xxSuccessful.shouldBeTrue()
            }
    }
})