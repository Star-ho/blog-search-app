package com.kakao.task.searchKeyword

import com.kakao.task.externalApi.SearchRequest
import com.kakao.task.externalApi.BlogSearchResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class BlogSearchController(
        private val blogSearchService: BlogSearchService
) {
    @GetMapping("/blog/search")
    fun searchBlog(searchRequest: SearchRequest): BlogSearchResponse? {
        return blogSearchService.getData(searchRequest)
    }

    @GetMapping("/popular-search-keyword")
    fun getPopularSearchKeyword(): List<GetPopularSearchKeywordResponse> {
        return blogSearchService.getPopularSearchKeyword()
    }
}

//TODO: 2023/03/20 exception hander 만들기  - 성호
//TODO: 2023/03/20 system core로 옮기기  - 성호
//@Converter(autoApply = true)
//class ZonedDateTimePersistenceConverter() : AttributeConverter<ZonedDateTime, Date> {
//    override fun convertToDatabaseColumn(datetime: ZonedDateTime?): Date? {
//        return datetime?.let { Date.from(it.toInstant()) }
//    }
//
//    override fun convertToEntityAttribute(date: Date?): ZonedDateTime? {
//        return date?.let { ZonedDateTime.of(LocalDateTime.ofInstant(it.toInstant(), App.APP_ZONE_ID), App.APP_ZONE_ID) }
//    }
//}