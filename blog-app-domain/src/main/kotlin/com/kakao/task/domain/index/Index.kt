package com.kakao.task.domain.index

//TODO: 2023/03/19 삭제 고민  - 성호
class Index(
        val name:IndexName,
        sequence: Long
){
    val sequence = Sequence(sequence)
}

class Sequence(sequence:Long){
    val value:Long
    init {
        if(sequence<0)throw IllegalArgumentException("시퀀스는 0보다 작을 수 없습니다")
        this.value = sequence
    }
}
enum class IndexName{
    LAST_BATCH_INDEX
}