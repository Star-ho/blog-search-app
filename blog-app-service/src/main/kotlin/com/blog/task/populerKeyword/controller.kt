package com.blog.task.populerKeyword

import com.blog.task.domain.popularKeyword.GetPopularSearchKeywordResponse
import com.blog.task.domain.popularKeyword.PopularKeywordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class PopularKeywordController(
        private val popularKeywordService: PopularKeywordService
){
    @GetMapping("/popular-keyword")
    fun getPopularSearchKeyword(): ResponseEntity<List<GetPopularSearchKeywordResponse>> {
        return ResponseEntity.ok(popularKeywordService.getPopularSearchKeyword())
    }
}