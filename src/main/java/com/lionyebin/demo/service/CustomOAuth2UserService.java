package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.Member;
import com.lionyebin.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberJpaRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //토큰 출력 확인용.
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("Access Token: " + accessToken);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email=oAuth2User.getAttribute("email");
        String username=oAuth2User.getAttribute("name");

        //과제 : 여기서 findByEmail 에 걸리는 유저가 있으면 , username 을 새로 Set 해주면 된다.
        Member member = memberRepository.findByEmail(email)
                .map(existingMember -> {
                    existingMember.setUsername(username);
                    return existingMember;
                })
                .orElseGet(()-> Member.builder()
                        .email(email)
                        .username(username)
                        .password("")
                        .build());

//        if(member.getId()==null) {
        memberRepository.save(member);
//        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                "sub"
        );

    }

}
