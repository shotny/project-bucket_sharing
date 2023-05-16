package com.shotny.bucketsharing.web;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.buckets.BucketsRepository;
import com.shotny.bucketsharing.domain.buckets.dto.BucketResponseDto;
import com.shotny.bucketsharing.domain.buckets.dto.BucketSaveDto;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.items.dto.ItemResponseDto;
import com.shotny.bucketsharing.domain.items.dto.ItemSaveDto;
import com.shotny.bucketsharing.domain.items.dto.ItemUpdateDto;
import com.shotny.bucketsharing.domain.buckets.BucketsService;
import com.shotny.bucketsharing.domain.items.ItemsService;
import com.shotny.bucketsharing.domain.member.MemberService;
import com.shotny.bucketsharing.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/buckets")
@Controller
public class BucketsController {
    private final MemberService memberService;
    private final BucketsRepository bucketsRepository;
    private final BucketsService bucketsService;
    private final ItemsService itemsService;

    @Value("${jwt.secret}")
    private String secretKey;


    // 버킷 홈
    @GetMapping
    public String getBucketsForm(@CookieValue("token") String token, Model model) {
        String memberName = JwtUtil.getUsername(token, secretKey);
        List<BucketResponseDto> buckets = bucketsService.findAllByMember(memberName);
        model.addAttribute("buckets", buckets);
        return "buckets/buckets";
    }


    // 버킷 생성
    @GetMapping("/new")
    public String bucketForm() {
        return "buckets/bucket-save";
    }

    @PostMapping("/new")
    public String addBucket(@CookieValue("token")String token, BucketSaveDto dto, RedirectAttributes redirectAttributes){
        String memberName = JwtUtil.getUsername(token, secretKey);
        Buckets bucket = bucketsService.saveBucket(memberName, dto);
        redirectAttributes.addAttribute("bucketId", bucket.getId());
        return "redirect:/v1/buckets/{bucketId}/items";
    }


    // 버킷명 수정
    @GetMapping("/{bucketId}/rename")
    public String updateBucketNameForm(@PathVariable Long bucketId, Model model) {
        Buckets bucket = bucketsRepository.findById(bucketId).get();
        model.addAttribute("bucket", new BucketResponseDto(bucket));
        return "/buckets/bucket-edit";
    }

    @PostMapping("/{bucketId}/rename")
    public String updateBucketName(@PathVariable Long bucketId, @RequestParam String name, Model model){
        System.out.println();
        System.out.println("============== update name: "+name+" ====================");
        bucketsService.updateName(bucketId, name);
        Buckets bucket = bucketsRepository.findById(bucketId).get();
        List<ItemResponseDto> items = itemsService.findItemsByBucket(bucketId);
        model.addAttribute("items", items);
        model.addAttribute("bucket", bucket);
        return "redirect:/v1/buckets/"+bucketId+"/items";
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


    // 버킷 삭제
    @GetMapping("/{bucketId}/delete")
    public String updateBucketNameForm(@PathVariable Long bucketId){
        bucketsService.deleteBucket(bucketId);
        return "redirect:/v1/buckets";
    }


    // 아이템 등록
    @PostMapping("/{bucketId}/items")
    public String addItem(@PathVariable Long bucketId, ItemSaveDto itemSaveDto) {
        System.out.println("===== Item save : " + itemSaveDto.getContent() + " =====");
        Items item = itemsService.saveItem(bucketId, itemSaveDto);
        return "redirect:/v1/buckets/{bucketId}/items";
    }


    // 아이템 수정
    @GetMapping("/{bucketId}/items/edit/{itemId}")
    public String editItemForm(@PathVariable Long bucketId, @PathVariable Long itemId, Model model) {
        Items item = itemsService.findItem(itemId);
        Buckets bucket = bucketsRepository.findById(bucketId).get();
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


    // 아이템 삭제
    @GetMapping("/{bucketId}/items/delete/{itemId}")
    public String deleteItem(@PathVariable Long bucketId, @PathVariable Long itemId) {
        itemsService.deleteItem(itemId);
        return "redirect:/v1/buckets/{bucketId}/items";
    }


    // 아이템 구매 완료 체크
    @PostMapping("/{bucketId}/items/done/{itemId}")
    public String checkItem(@PathVariable Long bucketId, @PathVariable Long itemId){
        System.out.println("====== checkItem: "+itemId+" ========");
        itemsService.updateCheck(itemId);
        return "redirect:/v1/buckets/{bucketId}/items";
    }



//    @GetMapping("/{memberId}")
//    public String getBuckets(@CookieValue("token")String token, @PathVariable Long memberId, Model model, HttpServletRequest request) throws IOException {
//        System.out.println("=============== before bucketsService =================");
//        List<BucketResponseDto> buckets = bucketsService.findAllByMember(memberId);
//        System.out.println("=============== after bucketsService =================");
//        String token = request.getHeader("authorization").split(" ")[1];
//        String username = JwtUtil.getUsername(token, secretKey);
//        System.out.println("========== get name by token ============");
//        System.out.println("name: " + username);
//        Cookie[] cookies = request.getCookies();
//        if (cookies.length != 0) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    String str = URLDecoder.decode(cookie.getValue(), "UTF-8");
//                    System.out.println("================= get token form cookie =====================");
//                    System.out.println(str);
//                }
//            }
//        }
//        System.out.println("================= get token form param =====================");
//        System.out.println(token);
//        String memberName = JwtUtil.getUsername(token, secretKey);
//        List<BucketResponseDto> buckets = bucketsService.findAllByMember(memberName);
//        model.addAttribute("buckets", buckets);
//        return "buckets/buckets";
//    }


//버킷 공유
//버킷 공유자 조회 팝업	Get	/buckets/{memberId}/{bucketId}/sharing	bucket-sharing
}
