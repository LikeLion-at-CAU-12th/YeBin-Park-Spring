package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album {
    @Id
    @GeneratedValue
    @Column(name="album_id")
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name="artist_id")
    private Artist artist;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL) //앨범은 자신의 곡 목록 가짐
    private List<Song> songs = new ArrayList<>();
}
