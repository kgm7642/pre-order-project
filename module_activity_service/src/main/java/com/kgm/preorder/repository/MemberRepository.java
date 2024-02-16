package com.kgm.preorder.repository;

import com.kgm.preorder.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOptionalByEmail(String email);

    Member findByEmail(String email);

    Optional<Member> findById(Long id);
}


