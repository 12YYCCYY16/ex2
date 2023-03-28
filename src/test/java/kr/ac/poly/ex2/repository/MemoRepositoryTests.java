package kr.ac.poly.ex2.repository;

import jakarta.transaction.Transactional;
import kr.ac.poly.ex2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {
    @Autowired
    MemoRepository memorepository;

    @Test
    public void testClass() {
        System.out.println(memorepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memorepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100l;

        Optional<Memo> result = memorepository.findById(mno);
        System.out.println("===========================");
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    @Transactional
    public void testSelect2() {
        Long mno = 100l;

        Memo memo = memorepository.getOne(mno);
        System.out.println("===========================");
        System.out.println(memo);

    }

    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100).memoText("Update Text").build();
        System.out.println(memorepository.save(memo));
    }

    @Test
    public void testDelete(){
        Long mno = 100l;
        memorepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memorepository.findAll(pageable);
        System.out.println(result);
        System.out.println("======================");
        System.out.println("total pages : " + result.getTotalPages());//총페이지수
        System.out.println("total elements : " + result.getTotalElements());//총개체의 수
        System.out.println("page number in present : " + result.getNumber());//현재 페이지 번호
        System.out.println("counter per page  : " + result.getSize());//페이지당 개체(레코드수)
        System.out.println("has next page : " + result.hasNext());//다음페이지 존재여부
        System.out.println("is First Page : " + result.isFirst());//현재 페이지가 첫번째 페이지
        System.out.println("=========================");
        for (Memo memo: result.getContent()){
            System.out.println(memo);
        }
    }
    @Test
    public void testSort(){
        Sort sort = Sort.by("mno").descending();
        Pageable pageable =PageRequest.of(0,10,sort);
        Page<Memo> result = memorepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }
}