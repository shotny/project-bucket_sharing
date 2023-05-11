package com.shotny.bucketsharing;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.items.dto.ItemSaveDto;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import com.shotny.bucketsharing.domain.buckets.BucketsService;
import com.shotny.bucketsharing.domain.items.ItemsService;
import com.shotny.bucketsharing.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final BucketsService bucketsService;
    private final ItemsService itemsService;


    @PostConstruct
    public void init(){
        MemberSaveDto memberDto = new MemberSaveDto("test", "abc1234", "abc1234");
        BucketSaveDto bucketDto = new BucketSaveDto("testBucket");
        ItemSaveDto itemDto1 = new ItemSaveDto("그릭 요거트");
        ItemSaveDto itemDto2 = new ItemSaveDto("시나몬 가루");
        ItemSaveDto itemDto3 = new ItemSaveDto("바나나");

        memberService.saveMember(memberDto);
        Buckets bucket = bucketsService.saveBucket(memberDto.getName(), bucketDto);
        Items item = itemsService.saveItem(bucket.getId(), itemDto1);
        itemsService.saveItem(bucket.getId(), itemDto2);
        itemsService.saveItem(bucket.getId(), itemDto3);

        itemsService.updateCheck(item.getId());
    }
}
