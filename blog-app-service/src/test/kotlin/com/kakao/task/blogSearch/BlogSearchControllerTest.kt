package com.kakao.task.blogSearch

import com.kakao.task.domain.blogSearch.BlogSearchResponse
import com.kakao.task.jsonMapper
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import org.springframework.boot.test.context.SpringBootTest


@OptIn(InternalAPI::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BlogSearchControllerTest : FunSpec({
    test("블로그 검색 api test"){
        val client = HttpClient()

        client.get("http://localhost:8080/blog/search?query=test").let {
            it.status shouldBe HttpStatusCode.OK
            val response = jsonMapper.readValue(it.content.toByteArray(), BlogSearchResponse::class.java)
            response.documents.size shouldNotBe 0
        }
    }
})
