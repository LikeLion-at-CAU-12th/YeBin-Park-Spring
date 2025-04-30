package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.Member;
import com.lionyebin.demo.dto.Joinrequest;
import com.lionyebin.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Service 로 빈 등록
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    public Page<Member> getMembersByPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        //정렬기준을 추가해 줄 수 있음.
        return memberJpaRepository.findAll(pageable);
    }

    public void printMembersByPage(int page, int size){ //아직 CRUD 가 없어서 테스트용으로 프린트하는함수
        Page<Member> memberPage = getMembersByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername()+ member.getEmail());
        }
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 인코더 DI

    public void join(Joinrequest joinRequest) {
        if (memberJpaRepository.existsByUsername(joinRequest.getUsername())) {
            return; // 나중에는 예외 처리
        }

        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequest.getPassword()))
                .email(joinRequest.getEmail())
                .build();

        memberJpaRepository.save(member);
// 
//     public Page<Member> getMembersByAge(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
//         //정렬기준을 추가해 줄 수 있음.
//         return memberJpaRepository.findByAgeGreaterThanEqual(20, pageable);
//     }

//     public void printMembersAge(int page, int size) {
//         Page<Member> memberPage = getMembersByAge(page, size);
//         List<Member> members = memberPage.getContent();

//         for (Member member : members) {
//             System.out.println("ID: " + member.getId() + ", age:"+ member.getAge()+", Username: " + member.getUsername());
//         }
//     }

//     public Page<Member> getMembersByName(int page, int size) {
//         Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
//         //정렬기준을 추가해 줄 수 있음.
//         return memberJpaRepository.findByUsernameStartingWith("user1", pageable);
//     }

//     public void printMembersName(int page, int size) {
//         Page<Member> memberPage = getMembersByName(page, size);
//         List<Member> members = memberPage.getContent();


//         for (Member member : members) {
//             System.out.println("ID: " + member.getId()+", Username: " + member.getUsername());
//         }
    }
}