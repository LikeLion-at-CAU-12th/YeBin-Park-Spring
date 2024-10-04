package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.*;
import com.lionyebin.demo.dto.request.ArticleCreateRequestDto;
import com.lionyebin.demo.dto.request.ArticleUpdateRequestDto;
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
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

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

                categoryArticleRepository.save(categoryArticle);
            }
        }
        return article.getId();
    }


    //수정하는 메소드!! Request 받아서, log 에도 남기고 수정된 Response 반환해주기
    @Transactional
    public ArticleResponseDto updateArticle(ArticleUpdateRequestDto requestDto) {
        //delete 처럼 id 로 게시글 찾고 예외처리
        Article article = articleRepository.findById(requestDto.getArticleId())
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 게시글이 존재하지 않습니다."));

        article.setTitle(requestDto.getTitle()); // 제목 업데이트
        article.setContent(requestDto.getContent()); // 내용 업데이트

        //카테고리 추가해야함. 기존에 있던 카테고리 아티클 삭제 후 카테고리 아이디 예외처리 후 categoryarticle 에 추가
        categoryArticleRepository.deleteByArticle(article);

        List<Long> categoryIds = requestDto.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("해당 ID를 가진 카테고리가 없습니다."));

                CategoryArticle categoryArticle = CategoryArticle.builder()
                        .category(category)
                        .article(article)
                        .build();

                categoryArticleRepository.save(categoryArticle);
            }
        }
        // 변경된 게시글 저장
        articleRepository.save(article);

        //articleLog 생성관련
        ArticleLog articleLog = ArticleLog.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .article(article)
                .build();

        //새로 생성된 로그 저장
        articleLogRepository.save(articleLog);

        // 수정된 게시글 응답 반환
        return new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent());

    }

    //헤당 id 존재하는지 확인 후 에외처리, 삭제하고 메세지 반환
    //categoryarticle 에서도 삭제
    //articlelog 에서도 지워야 삭제가 되던데, 로그에는 남길 수 업슬까
    @Transactional
    public String deleteArticle(Long articleId) {
        //article 예외 처리 및 article 객체 갖고옴
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 게시글이 존재하지 않습니다."));

        categoryArticleRepository.deleteByArticle(article);
        articleLogRepository.deleteByArticle(article);
        articleRepository.deleteById(articleId);

        return "게시글 ID " + articleId + "가 삭제되었습니다.";
    }

    public List<ArticleResponseDto> findArticlesByMemberId(Long memberId) {
        List<Article> articles = articleRepository.findByMemberId(memberId);
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent()))
                .collect(Collectors.toList());
    }
}
