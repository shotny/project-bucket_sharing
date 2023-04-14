package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.BucketMembers;
import com.shotny.bucketsharing.domain.BucketMembersRepository;
import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.buckets.dto.BucketNameUpdateDto;
import com.shotny.bucketsharing.domain.buckets.dto.BucketResponseDto;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BucketsService {

    private final MemberRepository memberRepository;
    private final BucketsRepository bucketsRepository;
    private final BucketMembersRepository bucketMembersRepository;

    // 장바구니 생성
    public Buckets saveBucket(Long memberId, BucketSaveDto dto) {
        Buckets bucket = bucketsRepository.save(dto.toEntity(memberId));
        Member member = findMember(memberId);
        member.updateBucket(true);
        memberRepository.save(member);
        bucketMembersRepository.save(new BucketMembers(member, bucket));
        return bucket;
    }


    // 장바구니 이름 수정
    public void updateBucketName(Long bucketId, BucketNameUpdateDto dto) {
        Buckets bucket = findBucket(bucketId);
        bucket.updateName(dto.getName());
        bucketsRepository.save(bucket);
    }


    // 장바구니 목록 조회
    public List<BucketResponseDto> findAllBuckets() {
        List<Buckets> bucketsList = bucketsRepository.findAll();
        List<BucketResponseDto> bucketDtoList = new ArrayList<>();
        for (Buckets bucket : bucketsList) {
            bucketDtoList.add(new BucketResponseDto(bucket));
        }
        return bucketDtoList;
    }


    // 장바구니 삭제
    public void deleteBucket(Long id) {
        Buckets bucket = findBucket(id);
        bucketsRepository.delete(bucket);
    }


    // 공유중인 이용자 목록 조회
//    public void findUsingMembers(Long bucketId) {
//        Buckets bucket = findBucket(bucketId);
//        Long bucketMembersId = bucket.getBucketMembers().getId();
//        List<Member> bucketMembers = memberRepository.findByBucketMembersId(bucketMembersId);
//
//        // 리턴 어떤 값으로 할지 결정
//    }

// + 장바구니 다른 사람에게 공유

    public Member findMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id: " + memberId));
        return member;
    }

    public Buckets findBucket(Long bucketId) {
        Buckets bucket = bucketsRepository.findById(bucketId).orElseThrow(
                () -> new IllegalArgumentException("해당 장바구니가 존재하지 않습니다. id: " + bucketId));
        return bucket;
    }
}
