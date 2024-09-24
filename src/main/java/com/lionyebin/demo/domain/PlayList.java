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

    public void addSong(Song song){//빌드 패턴 한번 사용해본 예시,,,

        //.anyMath stream 안에서 내용이 하나라도 맞으면 true 반환-> all,none 보다 중복검사에 쓰기 적절
        boolean songExists=playListSongs.stream()
                .anyMatch(playListSong -> playListSong.getSong().equals(song));

        if(!songExists) { //PlayListSong에 중복된 song 이 없어요 ~ -> 추가하기
            PlayListSong playListSong = PlayListSong.builder()
                    .playList(this)
                    .song(song)
                    .build();
            this.playListSongs.add(playListSong);
        }
    }

    public void removeSong(Song song) { //해당된 Song 을 찾아서 이 PlayList 객체에 관련된 PlayListSong 테이블 삭제.
        this.playListSongs.removeIf(playListSong->playListSong.getSong().equals(song));
    }


}
