package com.kakao.task.populerKeyword

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class PopularKeywordController(
        private val popularKeywordService: PopularKeywordService
){
    @GetMapping("/popular-search-keyword")
    fun getPopularSearchKeyword(): List<GetPopularSearchKeywordResponse> {
        return popularKeywordService.getPopularSearchKeyword()
    }
}