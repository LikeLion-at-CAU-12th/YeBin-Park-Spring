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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Song extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="song_id")
    private Long title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY) //곡과 아티스트 N:1
    @JoinColumn(name="artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY) //곡과 앨범 N:1
    @JoinColumn(name="album_id")
    private Album album;

    public void setArtist(Artist artist){
        this.artist= artist;
    }
    public void setAlbum(Album album){
        this.album= album;
    }
}
