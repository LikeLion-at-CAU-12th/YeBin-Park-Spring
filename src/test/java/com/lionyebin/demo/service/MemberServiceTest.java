package com.lionyebin.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void TestPrintMemberByPage(){
        memberService.printMembersByPage(0,10);
    }
}
