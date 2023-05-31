package com.study.ipa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter

//jpa연관관계 매핑에서는 연관관계 데이터는 ToString에서 제외해야 한다.
@ToString(exclude = {"employees"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    //양방향 매핑에서는 상대방 엔티티의 갱신에 관여할 수 없다.
    //단순한 읽기전용(조회)으로만 사용해야 함.
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

}
