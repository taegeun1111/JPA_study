package com.study.ipa.pagination.repository;

import com.study.ipa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //JPA는 Insert,Update,Delete시에 Transactional이 필수
                //서비스 클래스 사용 시 해당 클래스에 명시해야 한다.
@Rollback(false)
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert(){
        //학생을 147명 저장
        for (int i = 1; i < 148; i++) {
            Student sq = Student.builder()
                    .name("김파파 " + i)
                    .city("도시 " + i)
                    .major("전공 " + i)
                    .build();
            studentPageRepository.save(sq);
        }
    }

    @Test
    @DisplayName("기본 페이징 테스트")
    void testBasicPagination(){
        //given
        int pageNo = 1;
        int amount = 10;

        //페이지 정보 생성 (정렬기준 필드명)
        Pageable pageInfo = PageRequest.of(pageNo-1, amount, Sort.by("name").descending());

        //when
        Page<Student> students = studentPageRepository.findAll(pageInfo);

        //페이징 완료된 데이터셋
        List<Student> studentList = students.getContent();

        //총 학생 수
        long elements = students.getTotalElements();

        //then
        System.out.println("\n\n\n");
        System.out.println("elements = " + elements);
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
        Pageable pageable = students.getPageable();
        System.out.println("prev = " + pageable.previousOrFirst());
        System.out.println("next = " + pageable.next());
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("이름검색 + 페이징")
    void testSearchAndPagination(){
        //given
        int pageNo=1;
        int size = 10;
        Pageable pageInfo = PageRequest.of(pageNo - 1, size);

        //when
        Page<Student> byNameContaining = studentPageRepository.findByNameContaining("3", pageInfo);

        //then
        System.out.println("\n\n\n");
        byNameContaining.getContent().forEach(System.out::println);
        System.out.println("\n\n\n");
    }
}