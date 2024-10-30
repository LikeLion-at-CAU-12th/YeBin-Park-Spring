package com.lionyebin.demo.service;

import com.lionyebin.demo.domain.Member;
import com.lionyebin.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = (Member) memberJpaRepository.findByUsername(username);

        return new User(member.getUsername(), member.getPassword(), new ArrayList<>());
    }

}
