package com.blog.task.populerKeyword


import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PopularKeywordControllerTest : FunSpec({
    test("블로그 검색 api test"){
        val client = HttpClient()

        client.get("http://localhost:8080/popular-keyword").let {
            it.status shouldBe HttpStatusCode.OK
        }
    }
})