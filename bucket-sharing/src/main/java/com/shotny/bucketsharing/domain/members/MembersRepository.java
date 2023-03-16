package com.shotny.bucketsharing.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembersRepository extends JpaRepository<Members, Long> {
    List<Members> findByBucketMembersId(Long bucketMembersId);
}
