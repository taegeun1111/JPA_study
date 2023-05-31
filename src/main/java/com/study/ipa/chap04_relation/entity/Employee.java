package com.study.ipa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString(exclude = {"department"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Builder
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String name;

    //EAGER : 항상 무조건 조인을 수행
    //LAZY : 필요한 경우에만 조인을 수행(묵인)
    //단방향 연관관계
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;
}
