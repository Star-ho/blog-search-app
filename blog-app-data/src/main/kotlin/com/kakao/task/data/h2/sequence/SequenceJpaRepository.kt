package com.kakao.task.data.h2.sequence

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor


@Entity
@Table(name = "SEQUENCE")
class SequenceDao(
        @Id
        @Enumerated(EnumType.STRING)
        @Column(name = "NAME")
        val name:SequenceKeyword,
        @Column(name = "SEQUENCE")
        val sequence:Long,
){
        enum class SequenceKeyword{
                LAST_UPDATE_SEARCH_KEYWORD_LOG_SEQUENCE
        }
}

interface SequenceJpaRepository: JpaRepository<SequenceDao, SequenceDao.SequenceKeyword>, JpaSpecificationExecutor<SequenceDao>