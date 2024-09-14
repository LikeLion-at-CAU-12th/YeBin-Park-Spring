package com.lionyebin.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private long id;

    @Column(nullable = true)
    private String username;
    private String email;
    private int age;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) //한멤버는 여러개의 플리생성
    private List<PlayList> playlists = new ArrayList<>();
    public void addPlaylist(PlayList playlist){ // 플레이리스트 생성함수
        this.playlists.add(playlist);
        playlist.setMember(this); //이게 없으면 PlayList 의 Member 가 null이 되버릴수도 있단겨
    }

    public void removePlaylist(PlayList playList){ //플레이리스트 삭제함수
        this.playlists.remove(playList);
        playList.setMember(null);
    }

}
