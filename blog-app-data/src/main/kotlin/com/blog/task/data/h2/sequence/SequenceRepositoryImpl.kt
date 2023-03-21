package com.blog.task.data.h2.sequence

import com.blog.task.domain.sequence.SequenceRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class SequenceRepositoryImpl(
        private val sequenceJpaRepository: SequenceJpaRepository
):SequenceRepository{
    @OptIn(ExperimentalStdlibApi::class)
    override fun findLastUpdateSearchSequence(): Long {
        return sequenceJpaRepository.findById(SequenceDao.SequenceKeyword.LAST_UPDATE_SEARCH_KEYWORD_LOG_SEQUENCE).getOrNull()?.sequence?:1L
    }

    override fun saveLastUpdateLogSequence(sequence:Long){
        sequenceJpaRepository.save(SequenceDao(SequenceDao.SequenceKeyword.LAST_UPDATE_SEARCH_KEYWORD_LOG_SEQUENCE,sequence))
    }
}
