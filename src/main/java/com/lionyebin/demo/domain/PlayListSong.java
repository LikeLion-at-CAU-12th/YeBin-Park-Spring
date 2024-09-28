package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayListSong { //플레이 리스트와 곡이 다대다 관계이므로 만드는 테이블
    @Id
    @GeneratedValue
    @Column(name = "playlist_song_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private PlayList playList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @Builder //얘가 있어야 PlayList 에서 Song 을 추가하는 메소드를 만들때 빌더패턴으로 쉽게 객체를 추가할 수 있다고 함
    public PlayListSong(PlayList playList, Song song) {
        this.playList = playList;
        this.song = song;
    }
}
