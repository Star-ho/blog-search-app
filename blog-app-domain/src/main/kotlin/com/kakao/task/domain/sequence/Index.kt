package com.kakao.task.domain.sequence

//TODO: 2023/03/19 삭제 고민  - 성호
class Index(
        val name:SequenceName,
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
enum class SequenceName{
    LAST_BATCH_INDEX
}