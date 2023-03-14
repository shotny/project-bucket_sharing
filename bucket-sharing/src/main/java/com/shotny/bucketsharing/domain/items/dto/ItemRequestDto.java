package com.shotny.bucketsharing.domain.items.dto;

import com.shotny.bucketsharing.domain.items.Items;
import lombok.Getter;

@Getter
public class ItemRequestDto {
    private String content;

    public Items toEntity() {
        return Items.builder()
                .content(content)
                .build();
    }
}
