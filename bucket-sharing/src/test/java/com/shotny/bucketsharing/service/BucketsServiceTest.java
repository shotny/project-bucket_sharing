package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.BucketMembersRepository;
import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.buckets.dto.BucketNameUpdateDto;
import com.shotny.bucketsharing.domain.buckets.dto.BucketResponseDto;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import com.shotny.bucketsharing.domain.members.Members;
import com.shotny.bucketsharing.domain.members.MembersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BucketsServiceTest {

    @Autowired MembersRepository membersRepository;
    @Autowired BucketsRepository bucketsRepository;
    @Autowired BucketsService bucketsService;
    @Autowired BucketMembersRepository bucketMembersRepository;

    @AfterEach
    void clearAll() {
        bucketsRepository.deleteAll();
        membersRepository.deleteAll();
    }

    @Test
    void 장바구니생성() {
        //given
        Members member = membersRepository.save(new Members("member1"));

        //when
        Buckets bucket = bucketsService.saveBucket(member.getId(), new BucketSaveDto("bucket1"));
        Buckets findBucket = bucketsRepository.findById(bucket.getId()).get();

        //then
        Assertions.assertThat(findBucket.getName()).isEqualTo("bucket1");
    }

    @Test
    void 장바구니명_수정() {
        //given
        Members member = membersRepository.save(new Members("member1"));
        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));

        //when
        BucketNameUpdateDto dto = new BucketNameUpdateDto("Bucket edited");
        bucketsService.updateBucketName(bucket.getId(), dto);
        Buckets edited = bucketsService.findBucket(bucket.getId());

        //then
        Assertions.assertThat(edited.getName()).isEqualTo("Bucket edited");
    }

    @Test
    void 모든장바구니_조회() {
        //given
        Members member = membersRepository.save(new Members("member1"));
        Buckets bucket1 = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
        Buckets bucket2 = bucketsRepository.save(new BucketSaveDto("bucket2").toEntity(member.getId()));
        Buckets bucket3 = bucketsRepository.save(new BucketSaveDto("bucket3").toEntity(member.getId()));

        //when
        List<BucketResponseDto> allBuckets = bucketsService.findAllBuckets();

        //then
        Assertions.assertThat(allBuckets.size()).isEqualTo(3);
    }

    @Test
    void 장바구니_삭제() {
        //given
        Members member = membersRepository.save(new Members("member1"));
        Buckets bucket1 = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
        Buckets bucket2 = bucketsRepository.save(new BucketSaveDto("bucket2").toEntity(member.getId()));

        //when
        bucketsService.deleteBucket(bucket2.getId());

        //then
        Assertions.assertThat(bucketsService.findAllBuckets().size()).isEqualTo(1);
//        Assertions.assertThat(bucketsService.findBucket(bucket2.getId())).fail("해당 장바구니가 존재하지 않습니다. id: 2");
    }

//    @Test
//    void 공유중인멤버_조회() {
//        //given
//        Members member = new Members();
//
//        //when
//
//
//        //then
//    }


//        @Test
//    void 체크된아이템_조회() {
//        //given
//        Members member = membersRepository.save(new Members("member1"));
//        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
//        Items saved1 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("checked item1"));
//        Items saved2 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("checked item2"));
//        Items saved3 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("unchecked item"));
//
//        //when
//        itemsService.updateCheck(saved1.getId());
//        itemsService.updateCheck(saved2.getId());
//
//        //then
//
//    }
}