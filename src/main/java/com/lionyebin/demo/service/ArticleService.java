package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.*;
import com.lionyebin.demo.dto.request.ArticleCreateRequestDto;
import com.lionyebin.demo.dto.response.ArticleResponseDto;
import com.lionyebin.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private ArticleJpaRepository articleRepository;
    @Autowired
    private CategoryJpaRepository categoryRepository;
    @Autowired
    private ArticleLogJpaRepository articleLogRepository;
    @Autowired
    private CategoryArticleJpaRepository categoryArticleRepository;

    //어디서든 접근가능 -> public
    @Transactional
    public Long createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(()->new RuntimeException("해당 아이디를 가진 회원이 존재하지 않습니다."));
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .comments(new ArrayList<>())
                .build();
        articleRepository.save(article);

        ArticleLog articleLog = ArticleLog.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .article(article)
                .build();
        articleLogRepository.save(articleLog);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if(categoryIds != null && !categoryIds.isEmpty()){
            for(Long categoryId : categoryIds){
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(()->new RuntimeException("해당 ID 를 가진 카테고리가 없습니다"));

                CategoryArticle categoryArticle= CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();
            }
        }
        return article.getId();
    }
    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId);
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }
}
