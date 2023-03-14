package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.items.ItemsRepository;
import com.shotny.bucketsharing.domain.items.dto.ItemRequestDto;
import com.shotny.bucketsharing.domain.items.dto.ItemResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ItemsService {
    private final ItemsRepository itemsRepository;
    private final BucketsRepository bucketsRepository;


    public void saveItem(Long bucketId, ItemRequestDto dto) {
        Buckets bucket = findBucket(bucketId);
        Items items = dto.toEntity();
        items.setBucket(bucket);
        itemsRepository.save(items);

        bucket.countUpItem();
        bucketsRepository.save(bucket);
    }


    public void updateItem(Long id, ItemRequestDto dto) {
        Items item = findItem(id);
        item.updateContent(dto.getContent());
        itemsRepository.save(item);
    }


    public void updateCheck(Long id) {
        Items item = findItem(id);
        item.updateCheck();
        itemsRepository.save(item);

        Buckets bucket = findBucket(item.getBucket().getId());
        bucket.updateChecked(item.isCheck());
        bucketsRepository.save(bucket);
    }


    public void deleteItem(Long id) {
        Items item = findItem(id);
        itemsRepository.delete(item);

        Buckets bucket = findBucket(item.getBucket().getId());
        bucket.countDownItem();
        bucketsRepository.save(bucket);
    }


    public List<ItemResponseDto> findItemsByBucket(Long bucketId) {
        List<Items> itemList = itemsRepository.findByBucketId(bucketId);
        List<ItemResponseDto> itemDtoList = new ArrayList<>();

        for (Items item : itemList) {
            itemDtoList.add(new ItemResponseDto(item));
        }

        return itemDtoList;
    }


    public Buckets findBucket(Long bucketId) {
        Buckets bucket = bucketsRepository.findById(bucketId).orElseThrow(
                () -> new IllegalArgumentException("해당 장바구니가 존재하지 않습니다. id: " + bucketId));
        return bucket;
    }

    public Items findItem(Long id) {
        Items item = itemsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 리스트가 존재하지 않습니다. id: " + id));
        return item;
    }
}
