package com.lionyebin.demo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleCreateRequestDto { //Request 를 받을때 body 에 어떤 내용을 받을 지 명세.
    private Long memberId;
    private String title;
    private String content;
    private List<Long> categoryIds;
}
