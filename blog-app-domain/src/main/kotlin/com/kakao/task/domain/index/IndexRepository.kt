package com.kakao.task.domain.index

interface IndexRepository{
    fun findLastUpdateSearchIndex(): Long
    fun saveLastUpdateLogIndex(index:Long)
}