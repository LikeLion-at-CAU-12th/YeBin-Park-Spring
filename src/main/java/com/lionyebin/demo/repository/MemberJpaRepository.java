package com.lionyebin.demo.repository;

import com.lionyebin.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//인터페이스로 만들기 !!!
@Repository
public interface MemberJpaRepository extends JpaRepository<Member,Long> {//어떤 엔티티에 대한 레포지토리인지, pk 타입

    List<Member> findByUsername(String username);

}
