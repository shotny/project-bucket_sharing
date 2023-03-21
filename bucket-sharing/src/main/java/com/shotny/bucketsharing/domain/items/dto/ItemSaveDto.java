package com.shotny.bucketsharing.domain.items.dto;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.items.Items;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemSaveDto {
    private String content;

    public Items toEntity(Buckets bucket) {
        return Items.builder()
                .content(content)
                .bucket(bucket)
                .build();
    }
}
