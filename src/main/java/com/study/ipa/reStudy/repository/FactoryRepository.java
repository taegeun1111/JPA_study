package com.study.ipa.reStudy.repository;

import com.study.ipa.reStudy.Factory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactoryRepository extends JpaRepository<Factory,String> {

    List<Factory> findByName(String name);

    void deleteByName(String name);
}
