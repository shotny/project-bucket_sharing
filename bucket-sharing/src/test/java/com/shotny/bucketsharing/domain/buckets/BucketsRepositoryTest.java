package com.shotny.bucketsharing.domain.buckets;

import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BucketsRepositoryTest {

    @Autowired BucketsRepository bucketsRepository;

    @AfterEach
    public void clearAll() {
        bucketsRepository.deleteAll();
    }

    @Test
    public void saveBucket() {
        //given
        BucketSaveDto dto = new BucketSaveDto("bucket1");
//        Buckets bucket = dto.toEntity();

        //when
//        Buckets savedBucket = bucketsRepository.save(bucket);

        //then
//        Assertions.assertThat(savedBucket.getId()).isEqualTo(1);
//        Assertions.assertThat(savedBucket.getName()).isEqualTo("bucket1");
    }
}