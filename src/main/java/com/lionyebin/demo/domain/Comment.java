package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

}
