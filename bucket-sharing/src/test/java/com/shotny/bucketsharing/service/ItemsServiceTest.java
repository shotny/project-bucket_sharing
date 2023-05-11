package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.items.ItemsRepository;
import com.shotny.bucketsharing.domain.items.ItemsService;
import com.shotny.bucketsharing.domain.items.dto.ItemResponseDto;
import com.shotny.bucketsharing.domain.items.dto.ItemSaveDto;
import com.shotny.bucketsharing.domain.items.dto.ItemUpdateDto;
import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ItemsServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired BucketsRepository bucketsRepository;
    @Autowired
    ItemsService itemsService;
    @Autowired ItemsRepository itemsRepository;

    @AfterEach
    void clearAll() {
        memberRepository.deleteAll();
        bucketsRepository.deleteAll();
        itemsRepository.deleteAll();
    }

    @Test
    void 아이템_저장() {
        //given
        Member member = memberRepository.save(new Member("member1"));
        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));

        //when
        Items saved = itemsService.saveItem(bucket.getId(), new ItemSaveDto("item1"));

        //then
        Assertions.assertThat(itemsService.findBucket(bucket.getId()).getItemCount()).isEqualTo(1);
        Assertions.assertThat(itemsRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(itemsService.findItem(saved.getId()).getContent()).isEqualTo("item1");
    }

    @Test
    void 아이템_수정() {
        //given
        Member member = memberRepository.save(new Member("member1"));
        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
        Items saved = itemsService.saveItem(bucket.getId(), new ItemSaveDto("item1"));

        //when
        itemsService.updateItem(saved.getId(), new ItemUpdateDto("edit!"));

        //then
        Assertions.assertThat(itemsService.findItem(saved.getId()).getContent()).isEqualTo("edit!");
    }
    @Test
    void 아이템_체크() {
        //given
        Member member = memberRepository.save(new Member("member1"));
        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
        Items saved1 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("checked item"));
        Items saved2 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("unchecked item2"));

        //when
        itemsService.updateCheck(saved1.getId());

        //then
        Assertions.assertThat(itemsService.findItem(saved1.getId()).isChecked()).isTrue();
        Assertions.assertThat(itemsService.findItem(saved2.getId()).isChecked()).isFalse();
    }


    @Test
    void 아이템_삭제() {
        //given
        Member member = memberRepository.save(new Member("member1"));
        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
        Items saved1 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("checked item"));
        Items saved2 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("unchecked item2"));

        //when
        itemsService.deleteItem(saved2.getId());

        //then
        Assertions.assertThat(itemsRepository.findAll().size()).isEqualTo(1);
        // 예외적용 에러메세지 테스트 찾아보기
    }

    @Test
    void 장바구니속_아이템_전체조회() {
        //given
        Member member = memberRepository.save(new Member("member1"));
        Buckets bucket = bucketsRepository.save(new BucketSaveDto("bucket1").toEntity(member.getId()));
        Items saved1 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("checked item"));
        Items saved2 = itemsService.saveItem(bucket.getId(), new ItemSaveDto("unchecked item2"));

        //when
        List<ItemResponseDto> items = itemsService.findItemsByBucket(bucket.getId());

        //then
        Assertions.assertThat(items.size()).isEqualTo(2);
    }


}