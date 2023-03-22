package com.blog.task.externalApi

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.util.function.Consumer

@Component
class BlogWebClient {
    fun <T> get(uri: URI, headers: Consumer<HttpHeaders>, responseClass:Class<T>): T? {
        return WebClient.create().get()
            .uri(uri)
            .headers(headers)
            .retrieve()
            .bodyToMono(responseClass)
            .block()
    }
}