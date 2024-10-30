package com.lionyebin.demo.config;

import com.lionyebin.demo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //설정추가
        http
                .cors((SecurityConfig::corsAllow))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth)->auth //조인과 로그인은 모두에게 허용
                        .requestMatchers("/join", "login").permitAll()
                        .requestMatchers("/api/**").authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .userDetailsService(customUserDetailsService);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //CORS 메소드 설정 setAllow Methods, Origins, Headers 등등 세션자료 확인
    private static void corsAllow(CorsConfigurer<HttpSecurity> corsCustomizer) {
        corsCustomizer.configurationSource(request -> {

            CorsConfiguration configuration = new CorsConfiguration();
            // 커스텀 가능
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);

            return configuration;
        });
    }
    private final CustomUserDetailsService customUserDetailsService;

}
