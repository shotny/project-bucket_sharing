package com.shotny.bucketsharing.web;

import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
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
    public String login() {

        return "login";
    }
}
