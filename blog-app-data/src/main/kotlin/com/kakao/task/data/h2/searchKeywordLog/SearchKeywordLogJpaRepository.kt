package com.kakao.task.data.h2.searchKeywordLog

import com.kakao.task.domain.searchKeywordLog.SearchKeywordLog
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import java.time.ZonedDateTime


@Entity
@Table(name = "SEARCH_KEYWORD_LOG")
class SearchKeywordLogDao(
        @Id
        @GeneratedValue(strategy  = GenerationType.IDENTITY)
        @Column(name = "ID")
        val id:Long,
        @Column(name = "KEYWORD")
        val keyword:String,
        @Column(name = "CREATED_AT")
        val createdAt: ZonedDateTime = ZonedDateTime.now()
){
    companion object{
        fun of(searchKeywordLog: SearchKeywordLog): SearchKeywordLogDao {
            return searchKeywordLog.let { SearchKeywordLogDao(it.id,it.keyword.value,it.createdAt) }
        }
    }

    fun toEntity(): SearchKeywordLog {
        return SearchKeywordLog(id, keyword, createdAt)
    }
}

interface SearchKeywordLogJpaRepository: JpaRepository<SearchKeywordLogDao, Long>, JpaSpecificationExecutor<SearchKeywordLogDao>{
    @Query("select SKLD from SearchKeywordLogDao SKLD where SKLD.id > :id")
    fun findByGraterThanId(id:Long):List<SearchKeywordLogDao>
}