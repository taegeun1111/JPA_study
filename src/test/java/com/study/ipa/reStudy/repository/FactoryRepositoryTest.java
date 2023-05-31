package com.study.ipa.reStudy.repository;

import com.study.ipa.reStudy.Factory;
import com.study.ipa.reStudy.Factory.Kind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static com.study.ipa.reStudy.Factory.Kind.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class FactoryRepositoryTest {

    @Autowired
    FactoryRepository factoryRepository;

    @BeforeEach
    void insertData(){
        Factory b1 = Factory.builder()
                .name("홍길동")
                .kind(BOLT)
                .price(50000)
                .build();
        Factory b2 = Factory.builder()
                .name("스펀지")
                .kind(NUT)
                .price(20000)
                .build();
        Factory b3 = Factory.builder()
                .name("대길이")
                .kind(BOLT)
                .price(80000)
                .build();
        Factory b4 = Factory.builder()
                .name("이섭동")
                .kind(BOLT)
                .price(15000)
                .build();

        factoryRepository.save(b1);
        factoryRepository.save(b2);
        factoryRepository.save(b3);
        factoryRepository.save(b4);
    }

    @Test
    @DisplayName("이름이 홍길동인 학생의 정보를 조회")
    void testFindByName(){
        //given
        String name = "홍길동";

        //when
        List<Factory> all = factoryRepository.findAll();
        //then
        System.out.println("all = " + all);
    }

    @Test
    @DisplayName("5번째 데이터베이스에 저장")
    void testSave(){
        Factory factory = Factory.builder()
                .name("추가저장")
                .price(60000)
                .build();

        factoryRepository.save(factory);
    }

    @Test
    @DisplayName("id가")
    void testRemove(){
        String name = "홍길동";
        factoryRepository.deleteByName(name);
        System.out.println();
        System.out.println();
        System.out.println();
        List<Factory> all = factoryRepository.findAll();
        all.forEach(System.out::println);
    }
}
