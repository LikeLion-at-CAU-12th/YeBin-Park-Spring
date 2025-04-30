package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.Member;
import com.lionyebin.demo.repository.MemberJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private final Random random = new Random();

    //Member 100 개 생성 코드
    @BeforeEach
    public void setUp() {
        IntStream.range(0, 100).forEach(i -> {
            String username = "user" + random.nextInt(10000);
            String email = "user" + random.nextInt(10000) + "@example.com";
            int age = random.nextInt(3) + 18; //18세 부터 21세까지 생성되게 바꿔봤다.
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .build();
            memberJpaRepository.save(member);
        });
    }

    @Test
    public void testPrintMembersByPage() {
        //이 부분은 숫자 바꿔가면서 출력해보기
        memberService.printMembersByPage(1, 5);
    }

    //@forEach 때문에 이 테스트 전체를 돌리면 200행이 만들어지고, 함수 하나만 테스트하면 100행이 제대로 만들어진다.
    @Test
    public void testPrintMembersByAge() {
        memberService.printMembersAge(0,5);
    }

    @Test
    public void testPrintMembersByName(){
        memberService.printMembersName(0,5);
    }
    //사이즈를 엄청 크게도 해봤는데, 그 사이즈만큼 존재하지 않으면 존재하는것만 다 프린트 해주는듯.


//    @Test
//    public void TestPrintMemberByPage(){
//        memberService.printMembersByPage(0,10);
//    }
}
