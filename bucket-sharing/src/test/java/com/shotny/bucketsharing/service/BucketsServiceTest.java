package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.BucketMembersRepository;
import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import com.shotny.bucketsharing.domain.members.Members;
import com.shotny.bucketsharing.domain.members.MembersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
class BucketsServiceTest {

    @Autowired MembersRepository membersRepository;
    @Autowired BucketsRepository bucketsRepository;
    @Autowired BucketMembersRepository bucketMembersRepository;

    @Test
    void 장바구니생성() {
    }

    @Test
    void updateBucketName() {
    }

    @Test
    void findAllBuckets() {
    }

    @Test
    void deleteBucket() {
    }

    @Test
    void findUsingMembers() {
    }

    @Test
    void findMember() {
    }

    @Test
    void findBucket() {
    }
}