package com.kakao.task.data.h2.searchKeyword

import com.kakao.task.BlogAppApplication
import com.kakao.task.domain.searchKeyword.SearchKeyword
import com.kakao.task.domain.searchKeyword.SearchKeywordRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@SpringBootTest(classes = [BlogAppApplication::class])
@Transactional
class SearchKeywordRepositoryTest(
        @Autowired private val searchKeywordRepository: SearchKeywordRepository,
        @Autowired private val entityManager: EntityManager,
): DescribeSpec() {

    init {
        describe("searchKeywordRepository 테스트"){

            context("인기 검색어 테스트") {
                it("검색 수 상위 10개 조회 테스트") {
                    for (i in 1..100) {
                        entityManager.persist(SearchKeywordDao(0, "keyword$i", BigDecimal(i)))
                        entityManager.flush()
                        entityManager.clear()
                    }

                    val searchKeywordList = searchKeywordRepository.findByTop10SearchKeyword()

                    searchKeywordList.size shouldBe 10
                    searchKeywordList.forEachIndexed { index, searchKeyword ->
                        val curHitCount = 100 - index
                        searchKeyword.keyword.value shouldBe "keyword$curHitCount"
                        searchKeyword.hitCount.compareTo(BigDecimal(curHitCount)) shouldBe 0
                    }
                }

                it("검색 수 상위 6개 조회 테스트") {
                    for (i in 1..6) {
                        entityManager.persist(SearchKeywordDao(0, "keyword$i", BigDecimal(i)))
                        entityManager.flush()
                        entityManager.clear()
                    }

                    val searchKeywordList = searchKeywordRepository.findByTop10SearchKeyword()

                    searchKeywordList.size shouldBe 6
                    searchKeywordList.forEachIndexed { index, searchKeyword ->
                        val curHitCount = 6 - index
                        searchKeyword.keyword.value shouldBe "keyword$curHitCount"
                        searchKeyword.hitCount.compareTo(BigDecimal(curHitCount)) shouldBe 0
                    }
                }
            }

            context("키워드로 조회 테스트"){
                it("키워드가 존재할떄") {
                    val keyword = "keyword"
                    val hitCount = BigDecimal.TEN
                    entityManager.persist(SearchKeywordDao(0, keyword,hitCount))
                    entityManager.flush()
                    entityManager.clear()

                    val searchKeyword = searchKeywordRepository.findByKeyword(keyword)

                    searchKeyword shouldNotBe null
                    searchKeyword?.keyword?.value shouldBe keyword
                    searchKeyword?.hitCount?.compareTo(hitCount) shouldBe 0
                }

                it("키워드가 존재하지 않을때") {
                    val keyword = "keyword"

                    val searchKeyword = searchKeywordRepository.findByKeyword(keyword)

                    searchKeyword shouldBe  null
                }
            }

            context("키워드 저장 테스트"){
                it("키워드가 존재할떄") {
                    val keyword = "keyword"
                    val hitCount = BigDecimal.TEN
                    searchKeywordRepository.save(SearchKeyword(0, keyword,hitCount))

                    val searchKeywordDao = entityManager.createQuery(
                            "select SKD from SearchKeywordDao SKD where SKD.keyword = keyword",SearchKeywordDao::class.java
                    ).singleResult

                    searchKeywordDao.keyword shouldBe keyword
                    searchKeywordDao.hitCount.compareTo(hitCount) shouldBe 0
                }
            }

            context("id로 조회수 수정 테스트"){
                it("id가 존재할떄") {
                    val keyword = "keyword"
                    val hitCount = BigDecimal.TEN
                    entityManager.persist(SearchKeywordDao(0, keyword,hitCount))
                    entityManager.flush()
                    entityManager.clear()

                    val hitCountForAdd = BigDecimal(13)
                    searchKeywordRepository.updateSearchKeywordHitCount(1,hitCountForAdd)

                    val searchKeywordDao = entityManager.createQuery(
                            "select SKD from SearchKeywordDao SKD where SKD.keyword = keyword",SearchKeywordDao::class.java
                    ).singleResult
                    entityManager.flush()
                    entityManager.clear()

                    searchKeywordDao.keyword shouldBe keyword
                    searchKeywordDao.hitCount.compareTo(hitCount.plus(hitCountForAdd)) shouldBe 0
                }

                it("id가 존재하지 않을때") {
                    val hitCountForAdd = BigDecimal(13)

                    shouldThrow<JpaObjectRetrievalFailureException>{
                        searchKeywordRepository.updateSearchKeywordHitCount(3, hitCountForAdd)
                    }
                }
            }
        }
    }
}