package com.kakao.task.data.h2.sequence

import com.kakao.task.BlogAppApplication
import com.kakao.task.domain.sequence.SequenceRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest(classes = [BlogAppApplication::class])
@Transactional
class SequenceRepositoryTest(
        @Autowired private val sequenceRepository: SequenceRepository,
        @Autowired private val entityManager: EntityManager,
): DescribeSpec(){

    init {
        describe("SequenceRepository 테스트"){
            it("최근 업데이트한 인기 검색어 로그 조회"){
                val sequence = 1000L
                val sequenceDao = SequenceDao(SequenceDao.SequenceKeyword.LAST_UPDATE_SEARCH_KEYWORD_LOG_SEQUENCE, sequence)
                entityManager.persist(sequenceDao)
                entityManager.flush()
                entityManager.clear()

                val findSearchSequence = sequenceRepository.findLastUpdateSearchSequence()
                findSearchSequence shouldBe sequence
            }

            it("최근 업데이트한  id"){
                val sequence = 1000L
                sequenceRepository.saveLastUpdateLogSequence(sequence)

                val savedSequence = entityManager.find(SequenceDao::class.java,
                        SequenceDao.SequenceKeyword.LAST_UPDATE_SEARCH_KEYWORD_LOG_SEQUENCE)

                savedSequence.sequence shouldBe sequence
            }
        }
    }
}