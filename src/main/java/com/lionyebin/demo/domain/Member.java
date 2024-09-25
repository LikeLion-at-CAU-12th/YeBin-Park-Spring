package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private long id;

    @Column(nullable = false)
    private String username;
    private String email;
    private int age;

    @Builder
    public Member(String username ,String email, int age){
        this.username=username;
        this.email=email;
        this.age=age;
    }
}
