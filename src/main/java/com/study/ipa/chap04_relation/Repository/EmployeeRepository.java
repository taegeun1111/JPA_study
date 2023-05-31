package com.study.ipa.chap04_relation.Repository;

import com.study.ipa.chap04_relation.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
