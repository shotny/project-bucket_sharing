package com.shotny.bucketsharing.service;

import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
