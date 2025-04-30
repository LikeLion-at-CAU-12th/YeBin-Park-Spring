package com.lionyebin.demo.repository;

import com.lionyebin.demo.domain.Article;
import com.lionyebin.demo.domain.ArticleLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLogJpaRepository extends JpaRepository<ArticleLog, Long> {
    Optional<ArticleLog> findByArticle(Article article);

    void deleteByArticle(Article article);
}
