package com.shotny.bucketsharing.domain.buckets.dto;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BucketSaveDto {
    private String name;

    public Buckets toEntity(Long memberId) {
        return Buckets.builder()
                .name(name)
                .ownerId(memberId)
                .build();
    }
}
