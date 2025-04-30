package com.lionyebin.demo.controller;

import com.lionyebin.demo.dto.Joinrequest;
import com.lionyebin.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final MemberService memberService;

    @PostMapping("/join")
    public void join(@RequestBody Joinrequest joinRequest) {
        memberService.join(joinRequest);
    }
}
