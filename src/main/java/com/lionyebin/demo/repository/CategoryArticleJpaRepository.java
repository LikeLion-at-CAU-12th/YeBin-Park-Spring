package com.lionyebin.demo.repository;

import com.lionyebin.demo.domain.Article;
import com.lionyebin.demo.domain.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryArticleJpaRepository extends JpaRepository<CategoryArticle, Long> {
    List<CategoryArticle> findByArticle(Article article);

    void deleteByArticle(Article article);
}
