package com.shotny.bucketsharing.web.buckets;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.items.dto.ItemResponseDto;
import com.shotny.bucketsharing.domain.items.dto.ItemSaveDto;
import com.shotny.bucketsharing.domain.items.dto.ItemUpdateDto;
import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
import com.shotny.bucketsharing.service.BucketsService;
import com.shotny.bucketsharing.service.ItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/buckets")
@Controller
public class BucketsController {
    private final MemberRepository memberRepository;
    private final BucketsRepository bucketsRepository;
    private final BucketsService bucketsService;
    private final ItemsService itemsService;


    // 버킷 조회
    @GetMapping
    public String buckets(Model model) {
        List<Buckets> buckets = bucketsRepository.findAll();
        model.addAttribute("buckets", buckets);
        return "buckets/buckets";
    }


    // 버킷 생성
    @GetMapping("/new")
    public String addBucketForm(Model model) {
        return "buckets/bucket-save";
    }

    @PostMapping("/new")
    public String addBucket(BucketSaveDto dto, RedirectAttributes redirectAttributes){
        Buckets bucket = bucketsService.saveBucket(1L, dto);
        redirectAttributes.addAttribute("bucketId", bucket.getId());
        return "redirect:/v1/buckets/{bucketId}/items";
    }


    // 버킷 아이템 조회
    @GetMapping("/{bucketId}/items")
    public String itemsForm(@PathVariable Long bucketId, Model model) {
        Buckets bucket = bucketsRepository.findById(bucketId).get();
        List<ItemResponseDto> items = itemsService.findItemsByBucket(bucketId);
        model.addAttribute("items", items);
        model.addAttribute("bucket", bucket);
        return "/buckets/bucket-items";
    }


    // 아이템 등록
    @PostMapping("/{bucketId}/items")
    public String addItem(@PathVariable Long bucketId, ItemSaveDto itemSaveDto) {
        System.out.println("===== Item save : " + itemSaveDto.getContent() + " =====");
        Items item = itemsService.saveItem(bucketId, itemSaveDto);
        return "redirect:/v1/buckets/{bucketId}/items";
    }


    // 아이템 수정 폼
    @GetMapping("/{bucketId}/items/edit/{itemId}")
    public String editItemForm(@PathVariable Long bucketId, @PathVariable Long itemId, Model model) {
        Items item = itemsService.findItem(itemId);
        Buckets bucket = bucketsService.findBucket(bucketId);
        model.addAttribute("bucket", bucket);
        model.addAttribute("item", item);
        return "/buckets/item-edit";
    }

    @PostMapping("/{bucketId}/items/edit/{itemId}")
    public String editItem(@PathVariable Long bucketId, @PathVariable Long itemId, ItemUpdateDto dto) {
        itemsService.updateItem(itemId, dto);
//        redirectAttributes.addAttribute("bucketId", bucketId);
        return "redirect:/v1/buckets/{bucketId}/items";
    }


    @GetMapping("/{bucketId}/items/delete/{itemId}")
    public String deleteItem(@PathVariable Long bucketId, @PathVariable Long itemId) {
        itemsService.deleteItem(itemId);
        return "redirect:/v1/buckets/{bucketId}/items";
    }

    @PostMapping("/{bucketId}/items/done/{itemId}")
    public String checkItem(@PathVariable Long bucketId, @PathVariable Long itemId){
        System.out.println("====== checkItem: "+itemId+" ========");
        itemsService.updateCheck(itemId);
        return "redirect:/v1/buckets/{bucketId}/items";
    }

    // 버킷명 수정	Patch	/buckets/{memberId}/{bucketId}	buckets-save




// 버킷 생성 다른 방법
//    @PostMapping("/new")
//    public String addBucket(@RequestParam String name, RedirectAttributes redirectAttributes){
//        BucketSaveDto dto = new BucketSaveDto(name);
//        Buckets bucket = bucketsService.saveBucket(1L, dto);
//        Buckets savedBucket = bucketsRepository.save(bucket);
//        redirectAttributes.addAttribute("bucketId", savedBucket.getId());
//        return "redirect:/v1/buckets/{bucketId}/items";
//    }


    @PostConstruct
    public void initBucket() {
        Member member = memberRepository.save(new Member("test", "abc1234"));
        BucketSaveDto dto = new BucketSaveDto("testBucket1");
        Buckets bucket = bucketsRepository.save(dto.toEntity(member.getId()));

        Items firstItem = itemsService.saveItem(bucket.getId(), new ItemSaveDto("1st test item"));
        itemsService.saveItem(bucket.getId(), new ItemSaveDto("2nd test item"));
        itemsService.updateCheck(firstItem.getId());
    }

//버킷 삭제	Delete	/buckets/{memberId}/{bucketId}	redirect:/

//버킷 공유

//버킷 공유자 조회 팝업	Get	/buckets/{memberId}/{bucketId}/sharing	bucket-sharing
}
