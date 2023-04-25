package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
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

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 중복회원 검증

//    public int nameCheck(String name) {
//        if(memberRepository.findByName(name).get() == null){
//            return 0;
//        } return 1;
//    }
    public boolean nameCheck(String name) {
        if(memberRepository.findByName(name).isPresent()){
            return false;
        } return true;
    }
    // 비밀번호 일치 하면 회원가입

    public void save(MemberSaveDto dto) {
        dto.setEncodePassword(passwordEncoder.encode(dto.getPassword()));
        memberRepository.save(dto.toEntity());
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
