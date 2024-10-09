package com.lionyebin.demo.controller;

import com.lionyebin.demo.dto.request.ArticleCreateRequestDto;
import com.lionyebin.demo.dto.request.ArticleUpdateRequestDto;
import com.lionyebin.demo.dto.response.ArticleResponseDto;
import com.lionyebin.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<Long> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        Long articleId = articleService.createArticle(requestDto);
        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ArticleResponseDto>> getArticles(@PathVariable("memberId") Long memberId) {
        List<ArticleResponseDto> articles = articleService.findArticlesByMemberId(memberId);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articles);
    }

    //프론트 입장에서는 단순히 삭제 된 게시글의 아이디를 받는게 편할 수도 있을거같숩니다 메세지 안보내도 됐을듯
    @DeleteMapping("/{articleId}")
    public ResponseEntity<String> deleteArticle(@PathVariable("articleId") Long articleId) {
        String message = articleService.deleteArticle(articleId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ArticleResponseDto> updateArticle(@RequestBody ArticleUpdateRequestDto requestDto) {
        ArticleResponseDto updatedArticle = articleService.updateArticle(requestDto);
        return ResponseEntity.ok(updatedArticle);
    }
}
