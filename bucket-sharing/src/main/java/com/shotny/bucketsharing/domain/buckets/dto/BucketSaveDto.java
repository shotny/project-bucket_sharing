package com.shotny.bucketsharing.domain.buckets.dto;

import com.shotny.bucketsharing.domain.buckets.Buckets;

public class BucketSaveDto {
    private String name;

    public Buckets toEntity() {
        return Buckets.builder()
                .name(name)
                .build();
    }
}
