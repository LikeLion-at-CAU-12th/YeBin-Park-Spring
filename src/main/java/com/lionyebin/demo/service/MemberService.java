package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.Member;
import com.lionyebin.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            System.out.println("ID: " + member.getId() + ", Username: " + member.getUsername());
        }
    }

    public Page<Member> getMembersByAge(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        //정렬기준을 추가해 줄 수 있음.
        return memberJpaRepository.findByAgeGreaterThanEqual(20, pageable);
    }

    public void printMembersAge(int page, int size) {
        Page<Member> memberPage = getMembersByAge(page, size);
        List<Member> members = memberPage.getContent();


        for (Member member : members) {
            System.out.println("ID: " + member.getId() + ", age:"+ member.getAge()+", Username: " + member.getUsername());
        }
    }

    public Page<Member> getMembersByName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        //정렬기준을 추가해 줄 수 있음.
        return memberJpaRepository.findByUsernameStartingWith("user1", pageable);
    }

    public void printMembersName(int page, int size) {
        Page<Member> memberPage = getMembersByName(page, size);
        List<Member> members = memberPage.getContent();


        for (Member member : members) {
            System.out.println("ID: " + member.getId()+", Username: " + member.getUsername());
        }
    }
}
