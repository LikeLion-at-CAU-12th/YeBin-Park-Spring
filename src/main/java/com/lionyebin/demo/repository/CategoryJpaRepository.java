package com.lionyebin.demo.repository;

import com.lionyebin.demo.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
