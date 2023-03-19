package com.kakao.task.data.h2.index

import com.kakao.task.domain.index.IndexRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class IndexRepositoryImpl(
        private val indexJpaRepository: IndexJpaRepository
):IndexRepository{
    @OptIn(ExperimentalStdlibApi::class)
    override fun findLastUpdateSearchIndex(): Long {
        return indexJpaRepository.findById(IndexDao.IndexKeyword.LAST_UPDATE_SEARCH_KEYWORD_INDEX).getOrNull()?.sequence?:1L
    }

    override fun saveLastUpdateLogIndex(index:Long){
        indexJpaRepository.save(IndexDao(IndexDao.IndexKeyword.LAST_UPDATE_SEARCH_KEYWORD_INDEX,index))
    }
}
