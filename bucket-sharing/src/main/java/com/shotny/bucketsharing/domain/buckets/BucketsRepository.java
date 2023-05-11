package com.shotny.bucketsharing.domain.buckets;

import com.shotny.bucketsharing.domain.BucketMembers.BucketMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketsRepository extends JpaRepository<Buckets, Long> {
    //    Buckets findByOwnerId(Long Id);
}
