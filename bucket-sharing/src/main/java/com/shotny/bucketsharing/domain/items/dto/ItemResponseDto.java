package com.shotny.bucketsharing.domain.items.dto;

import com.shotny.bucketsharing.domain.items.Items;

public class ItemResponseDto {

    private Long id;
    private String content;
    private boolean checked;

    public ItemResponseDto(Items entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.checked = entity.isChecked();
    }
}
