package com.study.ipa.chap01_basic.entity.repository;


import com.study.ipa.chap01_basic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
