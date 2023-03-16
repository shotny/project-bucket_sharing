package com.shotny.bucketsharing.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class BucketsController {
// 버킷 생성	Post	/buckets/new/{memberId}	redirect:/

    @PostMapping("/buckets/new/{memberId}")
    public String addBucket(Long memberId) {

        return "redirect:/";
    }

//버킷명 수정	Patch	/buckets/{memberId}/{bucketId}	buckets-save

//버킷 아이템 조회	Get	/buckets/{memberId}/{bucketId}	buckets-items

//버킷 삭제	Delete	/buckets/{memberId}/{bucketId}	redirect:/

//버킷 공유

//버킷 공유자 조회 팝업	Get	/buckets/{memberId}/{bucketId}/sharing	bucket-sharing
}
