package com.kakao.task.data.h2.searchKeyword

import com.kakao.task.domain.searchKeyword.SearchKeyword
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

@Entity
@Table(name = "SEARCH_KEYWORD")
class SearchKeywordDao(
        @Id
        @GeneratedValue(strategy  = GenerationType.IDENTITY)
        @Column(name = "ID")
        val id:Long,
        @Column(name = "KEYWORD")
        val keyword:String,
        @Column(name = "HIT_COUNT")
        val hitCount:BigDecimal
){
        companion object{
                fun of(searchKeyword: SearchKeyword): SearchKeywordDao {
                        return searchKeyword.let { SearchKeywordDao(it.id,it.keyword.value, it.hitCount) }
                }
        }

        fun toEntity(): SearchKeyword {
                return SearchKeyword(id, keyword, hitCount)
        }
}

interface SearchKeywordJpaRepository: JpaRepository<SearchKeywordDao, Long>, JpaSpecificationExecutor<SearchKeywordDao>{

        @Query("select SKW from SearchKeywordDao SKW order by SKW.hitCount limit 10")
        fun findByTop10SearchKeyword():List<SearchKeywordDao>

        fun findByKeyword(keyword: String):SearchKeywordDao?

        @Modifying(clearAutomatically = true)
        @Query("update SEARCH_KEYWORD skd set skd.HIT_COUNT = skd.HIT_COUNT + :hitCount where skd.KEYWORD = :keyword", nativeQuery = true)
        fun updateHitCountByKeyword(keyword: String,@Param("hitCount") hitCount: BigDecimal)
}