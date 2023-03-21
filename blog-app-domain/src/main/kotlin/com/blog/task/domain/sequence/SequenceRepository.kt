package com.blog.task.domain.sequence

interface SequenceRepository{
    fun findLastUpdateSearchSequence(): Long
    fun saveLastUpdateLogSequence(sequence:Long)
}