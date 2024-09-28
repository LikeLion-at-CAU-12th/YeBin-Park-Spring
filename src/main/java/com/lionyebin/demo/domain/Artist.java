package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="artist_id")
    private long id;

    @Column(nullable = true)
    private String name;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL) //아티스트는 자신의 앨범목록가짐
    private List<Album> albums = new ArrayList<>();
}
