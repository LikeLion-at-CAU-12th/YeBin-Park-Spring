package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="playlist_id")
    private Long id;
    private String title;
    private String content;

    //다대다 매핑테이블인 PlayListSong 리스트
    //PlayListSong 안에 PlayList 와 Song 정보가 있음
    @OneToMany(mappedBy = "playList", cascade = CascadeType.ALL)
    private List<PlayListSong> playListSongs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) //Member:playlist 는 일대다
    @JoinColumn(name="member_id")
    private Member member;

    public void addSong(Song song){ //빌드 패턴 한번 사용해본 예시,,,
        PlayListSong playListSong= PlayListSong.builder()
                .playList(this)
                .song(song)
                .build();
    }

    public void removeSong(Song song) { //해당된 Song 을 찾아서 이 PlayList 객체에 관련된 PlayListSong 테이블 삭제.
        playListSongs.removeIf(playListSong->playListSong.getSong().equals(song));
    }


}
