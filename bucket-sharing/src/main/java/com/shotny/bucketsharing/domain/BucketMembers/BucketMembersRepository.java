package com.shotny.bucketsharing.domain.BucketMembers;

import com.shotny.bucketsharing.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketMembersRepository extends JpaRepository<BucketMembers, Long> {
    List<BucketMembers> findByMember(Member member);
//    Optional<BucketMembers> findByMember(Long memberId);
}
