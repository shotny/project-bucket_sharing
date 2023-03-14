package com.shotny.bucketsharing.domain.buckets.dto;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import lombok.Builder;
import lombok.Getter;

public class BucketResponseDto {

    private Long id;
    private String name;
    private int itemCount;
    private int checked;
    private int memberCount;

    public BucketResponseDto(Buckets entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.itemCount = entity.getItemCount();
        this.checked = entity.getChecked();
        this.memberCount = entity.getMemberCount();
    }
}
