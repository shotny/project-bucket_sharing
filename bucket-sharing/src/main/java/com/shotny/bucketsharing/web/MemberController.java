package com.shotny.bucketsharing.web;

import com.shotny.bucketsharing.domain.member.dto.LoginDto;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import com.shotny.bucketsharing.service.BucketsService;
import com.shotny.bucketsharing.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final BucketsService bucketsService;

    @GetMapping("/join")
    public String getJoinForm(Model model) {
        return "join";
    }


    @PostMapping("/nameCheck")
    @ResponseBody
    public boolean nameCheck(String name){
        System.out.println(name);
        return memberService.nameCheck(name);
    }

    @PostMapping("/join")
    public String join(MemberSaveDto memberSaveDto) {
        System.out.println("name: "+ memberSaveDto.getName());
        System.out.println("password: "+ memberSaveDto.getPassword());
        memberService.save(memberSaveDto);
        return "redirect:/v1/members/login";
    }

    @GetMapping("/login")
    public String loginForm() {

        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, LoginDto loginDto) {
        // 토큰발급 후 토큰에 저장된 유저정보로 유저 버킷 조회화면으로 넘어감
//        memberService.login(loginDto);
//        bucketsService.
//        model.addAttribute("buckets",)
        return "buckets/buckets";
    }


//    @GetMapping
//    public String buckets(Model model) {
//        List<Buckets> buckets = bucketsRepository.findAll();
//        model.addAttribute("buckets", buckets);
//        return "/buckets/buckets";
//    }

    @PostMapping("/inquiry")
    @ResponseBody
    public String inquiryMember(String name, String password) {
        System.out.println("name = " + name);
        System.out.println("password = " + password);
        memberService.inquiryMember(name, password);
        return "login";
    }
}
