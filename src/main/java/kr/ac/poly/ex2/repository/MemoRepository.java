package kr.ac.poly.ex2.repository;

import kr.ac.poly.ex2.entity.Memo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    //select  특정 mno값의 범위의 값들을 내림차순 정렬

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    //select  특정  mno값의 범위의 값들을 내림차순 정렬 + 페이징처리
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
    // delete 특정 mno 값보다 작은  행들을 삭제
    //작거나 같으면 lessthanequal을 사용
    void deleteMemoByMnoLessThan(Long num);
    //@Query를 사용하여 JPQL을 실행
    //SELECT문 실행

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();
    //@query 를 사용하여 jpql을 실행
    //update문 실행 (parameter로 jpql에 값 전달


    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText2(@Param("param") Memo memo);


    @Query(value = "select  m from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);
}
