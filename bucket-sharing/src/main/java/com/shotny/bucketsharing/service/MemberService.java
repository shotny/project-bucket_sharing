package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
import com.shotny.bucketsharing.domain.member.dto.LoginDto;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean nameCheck(String name) {
        if(memberRepository.findByName(name).isPresent()){
            return false;
        } return true;
    }

    public void save(MemberSaveDto dto) {
        dto.setEncodePassword(passwordEncoder.encode(dto.getPassword()));
        memberRepository.save(dto.toEntity());
    }

    public String inquiryMember(String name, String password) {
        // 입력받은 닉네임의 회원을 조회
        // 조회되는 회원이 없을 경우 non_existent
        // 조회한 회원의 비밀번호와 입력받은 비밀번호가 일치하지 않을 경우 wrong_password
        // 조회한 회원의 비밀번호와 입력받은 회원의 비밀번호가 일치하는 경우 토큰 발급
        Optional<Member> member = memberRepository.findByName(name);
        if(!member.isPresent()){
            System.out.println("non_existent");
            return "non_existent";
        }
        if (!passwordEncoder.matches(member.get().getPassword(), passwordEncoder.encode(password))) {
            System.out.println("wrong_password");
            return "wrong_password";
        }
        System.out.println("success");
        return "success";
    }

    public String login(LoginDto dto) {

        return "";
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username"));
        return toUserDetails(member);
    }

    private UserDetails toUserDetails(Member member) {
        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
//                .authorities(new SimpleGrantedAuthority())
                .build();
    }

}
