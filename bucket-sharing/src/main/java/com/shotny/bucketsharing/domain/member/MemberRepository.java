package com.shotny.bucketsharing.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
//    List<Member> findByBucketMembersId(Long bucketMembersId);

    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);
}
