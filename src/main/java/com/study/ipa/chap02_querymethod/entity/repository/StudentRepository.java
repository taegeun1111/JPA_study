package com.study.ipa.chap02_querymethod.entity.repository;

import com.study.ipa.chap02_querymethod.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, String> {

    //컬럼명이 아니라 필드명 꼭 지켜줘야한다.
    List<Student> findByName(String name);

    List<Student> findByCityAndMajor(String city, String major);

    List<Student> findByMajorContaining(String major);

    //네이티브 쿼리 사용
    @Query(value = "select * from tbl_student where stu_name = :nm", nativeQuery = true)
    Student findNameWithSQL(@Param("nm") String name);

    //JPQL
    //select 별칭 from 엔터티클래스명 as 별칭
    //where 별칭.필드명 = ?

    //native-sql : SELECT * FROM tbl_student
    //             WHERE stu_name = ?

    //jpql은 객체 대상
    //             SELECT st FROM Student as st
    //             WHERE st.name = ?

    //도시 이륾으로 학생 조회
    //?1을 사용하면 1번째 파라미터에 자동 기입
//    @Query(value = "SELECT * FROM tbl_student WHERE city = ?1", nativeQuery = true)

    @Query("SELECT s FROM Student s WHERE s.city = ?1")
    Student getByCityWithJPQL(String city);

    //@Param을 권장 : ?의 순서가 바뀔 수 있기 때문
    @Query("SELECT st FROM Student st WHERE st.name LIKE %:nm%")
    List<Student> searchByNamesWithJPQL(@Param("nm") String name);


}
