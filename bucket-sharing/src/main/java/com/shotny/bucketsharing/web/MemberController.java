package com.shotny.bucketsharing.web;

import com.shotny.bucketsharing.domain.member.dto.LoginDto;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import com.shotny.bucketsharing.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/v1/members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/join")
    public String getJoinForm() {
        return "join";
    }


    @PostMapping("/checkName")
    @ResponseBody
    public boolean checkName(String name) {
        System.out.println("nameCheck = " + name);
        return memberService.isUsableName(name);
    }


    @PostMapping("/join")
    public String join(MemberSaveDto memberSaveDto) {
        System.out.println("name: " + memberSaveDto.getName());
        System.out.println("password: " + memberSaveDto.getPassword());
        memberService.saveMember(memberSaveDto);
        return "redirect:/v1/members/login";
    }


    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }


    @ResponseBody
    @PostMapping("/loginInfo")
    public String checkInfo(LoginDto dto) {
        return memberService.matchLoginInfo(dto);
    }


    @PostMapping("/login")
    public String login(LoginDto dto, Model model, HttpServletResponse response) {

        String token = memberService.login(dto);
//        response.setHeader("authorization", "bearer " + token);

        Cookie cookie2 = new Cookie("token", token);
        response.addCookie(memberService.setCookieSecure(cookie2));

//        Member member = memberService.findMemberByName(dto.getName()).get();
        return "redirect:/v1/buckets";
    }


//    @PostMapping("/logout/{memberId}")
//    public String logout(@PathVariable Long memberId) {}

}
