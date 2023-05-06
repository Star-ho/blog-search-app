package com.blog.task.blogSearch

import com.blog.task.domain.blogSearch.SearchRequest
import com.blog.task.domain.blogSearch.BlogSearchResponse
import com.blog.task.domain.blogSearch.BlogSearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class BlogSearchController(
    private val blogSearchService: BlogSearchService,
) {
    @GetMapping("/blog/search")
    fun searchBlog(searchRequest: SearchRequest): ResponseEntity<BlogSearchResponse> {
        return ResponseEntity.ok(blogSearchService.getData(searchRequest))
    }
}


