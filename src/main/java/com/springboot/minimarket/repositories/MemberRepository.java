package com.springboot.minimarket.repositories;

import com.springboot.minimarket.models.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAllByDeletedAtIsNullOrderByName(Pageable pageable);
    Optional<Member> findByIdAndDeletedAtIsNull(Long id);
}
