package com.shotny.bucketsharing.web;

import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.dto.LoginDto;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import com.shotny.bucketsharing.service.BucketsService;
import com.shotny.bucketsharing.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    // 1.회원가입  2.로그인  3.로그아웃

    private final MemberService memberService;

    @GetMapping("/join")
    public String getJoinForm() {
        return "join";
    }

    @PostMapping("/checkName")
    @ResponseBody
    public boolean checkName(String name){
        System.out.println("nameCheck = " + name);
        return memberService.isUsableName(name);
    }

    @PostMapping("/join")
    public String join(MemberSaveDto memberSaveDto) {
        System.out.println("name: "+ memberSaveDto.getName());
        System.out.println("password: "+ memberSaveDto.getPassword());
        memberService.saveMember(memberSaveDto);
        return "redirect:/v1/members/login";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/test")
    public String checkInfo(LoginDto dto) {
        System.out.println("====== checkInfo ======");
        System.out.println("name: " + dto.getName());

        return memberService.matchLoginInfo2(dto);
    }
    @PostMapping("/login")
    public String login(LoginDto dto, Model model, HttpServletResponse response){

        System.out.println("====== login ======");
        System.out.println("name: "+dto.getName());

        String token = memberService.login(dto);
        response.setHeader("authorization", "bearer "+token);

        Member member = memberService.findMemberByName(dto.getName()).get();
        return "redirect:/v1/buckets/" + member.getId();
    }




//    @PostMapping("/logout/{memberId}")
//    public String logout(@PathVariable Long memberId) {}


//    private final BucketsService bucketsService;
//
//
//    @GetMapping("/join")
//    public String getJoinForm(Model model) {
//        return "join";
//    }


//
//    @GetMapping("/login")
//    public String loginForm() {
//
//        return "login";
//    }

//    @PostMapping("/login")
//    public String login(Model model, LoginDto loginDto, HttpServletResponse response) {
//        // 토큰발급 후 토큰에 저장된 유저정보로 유저 버킷 조회화면으로 넘어감
////        memberService.login(loginDto);
////        bucketsService.
////        model.addAttribute("buckets",)

//        System.out.println("password: "+loginDto.getPassword());
//
//        if (memberService.login(loginDto) == "false") {
//            return "false";
//        }
//        String createdToken = memberService.login(loginDto);
//        response.setHeader("authorization", "bearer " + createdToken);
//        Long memberId = memberService.findMemberId(loginDto.getName());
//        return "redirect:/v1/buckets/" + memberId;
//
//    }

//    @PostMapping("/login")
//    public String login(Model model, LoginDto loginDto, HttpServletResponse response) {
//        // 토큰발급 후 토큰에 저장된 유저정보로 유저 버킷 조회화면으로 넘어감
////        memberService.login(loginDto);
////        bucketsService.
////        model.addAttribute("buckets",)
//        System.out.println("====== login ======");
//        System.out.println("name: "+loginDto.getName());
//        System.out.println("password: "+loginDto.getPassword());
//
//        String createdToken = memberService.login(loginDto);
//        response.setHeader("authorization","bearer " + createdToken);
//        return "/buckets/buckets";
//    }


//    @GetMapping
//    public String buckets(Model model) {
//        List<Buckets> buckets = bucketsRepository.findAll();
//        model.addAttribute("buckets", buckets);
//        return "/buckets/buckets";
//    }

//    @PostMapping("/inquiry")
//    @ResponseBody
//    public String inquiryMember(String name, String password) {
//        System.out.println("name = " + name);
//        System.out.println("password = " + password);
//        return memberService.inquiryMember(name, password);
//    }

//    @PostMapping("/booleaninquiry")
//    @ResponseBody
//    public boolean booleaninquiryMember(String name, String password) {
//        System.out.println("name = " + name);
//        System.out.println("password = " + password);
//        return memberService.booleaninquiryMember(name, password);
//    }
}
