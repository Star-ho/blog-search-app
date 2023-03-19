package com.kakao.task.data.h2.index

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor


@Entity
@Table(name = "INDEX")
class IndexDao(
        @Id
        @Enumerated(EnumType.STRING)
        @Column(name = "NAME")
        val name:IndexKeyword,
        @Column(name = "SEQUENCE")
        val sequence:Long,
){
        enum class IndexKeyword{
                LAST_UPDATE_SEARCH_KEYWORD_INDEX
        }
}

interface IndexJpaRepository: JpaRepository<IndexDao, IndexDao.IndexKeyword>, JpaSpecificationExecutor<IndexDao>