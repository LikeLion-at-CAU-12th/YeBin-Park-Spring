package com.lionyebin.demo.repository;

import com.lionyebin.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    EntityManager em;
    @Test
    @Transactional
    void save() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);

    }

    @Test
    @Transactional
    @Rollback()
    public void testMember2() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        Long savedId = memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findOne(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId()); // 통과
        Assertions.assertThat(findMember).isEqualTo(member); // 실패
    }

    @Test
    @Transactional
    @Rollback()
    public void testFindAll() {
        Member member1 = Member.builder()
                .username("member1")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        Member member2 = Member.builder()
                .username("member2")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        Member member3 = Member.builder()
                .username("member3")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();


        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        em.flush();
        em.clear();

        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println(member.getId() + " " + member.getAge() + " " + member.getUsername());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByUsername() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("polarpheno@gmail.com")
                .build();

        Long savedId = memberRepository.save(member);

        em.flush();
        em.clear();

        List<Member> byUsername = memberRepository.findByUsername(member.getUsername());

        for (Member member1 : byUsername) {
            System.out.println(member1.getId());
        }
    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByUsername() {
    }
}