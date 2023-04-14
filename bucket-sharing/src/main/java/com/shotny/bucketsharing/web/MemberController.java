package com.shotny.bucketsharing.web;

import com.shotny.bucketsharing.service.MemberService;
import com.shotny.bucketsharing.web.members.form.JoinForm;
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
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new JoinForm());
        return "join";
    }

    @PostMapping("/join")
    public String join() {

        return "redirect:/v1/buckets";
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
