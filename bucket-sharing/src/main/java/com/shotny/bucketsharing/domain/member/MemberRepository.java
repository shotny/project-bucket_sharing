package com.shotny.bucketsharing.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    List<Member> findByBucketMembersId(Long bucketMembersId);

    Optional<Member> findByName(String name);

    Optional<Member> findById(Long id);

//    Member findByName(String name);
//    Member findById(Long id);
}
