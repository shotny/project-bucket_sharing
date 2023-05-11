package com.shotny.bucketsharing.domain.member;

import com.shotny.bucketsharing.domain.member.Member;
import com.shotny.bucketsharing.domain.member.MemberRepository;
import com.shotny.bucketsharing.domain.member.dto.LoginDto;
import com.shotny.bucketsharing.domain.member.dto.MemberSaveDto;
import com.shotny.bucketsharing.token.Token;
import com.shotny.bucketsharing.token.TokenRepository;
import com.shotny.bucketsharing.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder encoder;

    @Value("${jwt.secret}")
    private String secretKey;
    private Long expireTimeMs = 1000 * 60 * 30L;

    public void saveMember(MemberSaveDto dto) {
        dto.setEncodePassword(encoder.encode(dto.getPassword()));
        memberRepository.save(dto.toEntity());
    }



    public String matchLoginInfo(LoginDto dto) {
        String result = "success";
        Optional<Member> findMember = findMemberByName(dto.getName());

        if (findMember.isPresent()) {
            Member member = findMember.get();
            if (!isRightPassword(dto.getPassword(), member)) {
                result = "wrong_password";
            }
        } else result = "wrong_name";

        return result;
    }

    public String login(LoginDto dto) {
        Member member = findMemberByName(dto.getName()).get();
        String newToken = JwtUtil.createToken(member.getName(), secretKey, expireTimeMs);

//        saveNewToken(member, newToken);
        return newToken;
    }

    public void logout(){}

    public boolean isUsableName(String name){
        if (memberRepository.findByName(name).isPresent()) {
            return false;
        } else return true;
    }

    public boolean isRightPassword(String password, Member member){
        return encoder.matches(password, member.getPassword());
    }


    public Optional<Member> findMemberByName(String name) {

        Optional<Member> byName = memberRepository.findByName(name);
        System.out.println("======== findMemberByName =======");
        System.out.println("name: "+name+" is null?" + !byName.isPresent());
        return byName;
    }

    public Cookie setCookieSecure(Cookie cookie) {
        cookie.setSecure(true);
        cookie.isHttpOnly();
        cookie.setPath("/");
        return cookie;
    }

    public ResponseCookie getSecureCookie (String token) {
        ResponseCookie cookie = ResponseCookie.from("token", token)
//                                .maxAge(60*60*24*7) // 7DAYS
                                .path("/")
                                .secure(true)
                                .sameSite("None")
                                .httpOnly(true)
                                .build();
        return cookie;
    }

    public void saveNewToken(Member member, String newToken) {
        Optional<Token> findToken = tokenRepository.findById(member.getToken().getId());
        if (findToken.isPresent()) {
            tokenRepository.save(findToken.get().updateNewToken(newToken));
        } else {
            tokenRepository.save(
                    Token.builder()
                            .member(member)
                            .token(newToken)
                            .status(Token.TokenStatus.Login)
                            .build());
        }
    }


////    public boolean booleaninquiryMember(String name, String password) {
////        // 입력받은 닉네임의 회원을 조회
////        // 조회되는 회원이 없을 경우 non_existent
////        // 조회한 회원의 비밀번호와 입력받은 비밀번호가 일치하지 않을 경우 wrong_password
////        // 조회한 회원의 비밀번호와 입력받은 회원의 비밀번호가 일치하는 경우 토큰 발급
////        Optional<Member> member = memberRepository.findByName(name);
////        if(!member.isPresent()){
////            System.out.println("non_existent");
////            return false;
////        }
////        if (!passwordEncoder.matches(member.get().getPassword(), passwordEncoder.encode(password))) {
////            System.out.println("wrong_password");
////            return false;
////        }
////        System.out.println("success");
////        return true;
////    }
//
//
//    public String inquiryMember(String name, String password) {
//        // 입력받은 닉네임의 회원을 조회
//        // 조회되는 회원이 없을 경우 non_existent
//        // 조회한 회원의 비밀번호와 입력받은 비밀번호가 일치하지 않을 경우 wrong_password
//        // 조회한 회원의 비밀번호와 입력받은 회원의 비밀번호가 일치하는 경우 토큰 발급
////        Optional<Member> member = memberRepository.findByName(name);
////        if(!member.isPresent()){
////            System.out.println("non_existent");
////            return "non_existent";
////        }
////        if (!passwordEncoder.matches(member.get().getPassword(), passwordEncoder.encode(password))) {
////            System.out.println("wrong_password");
////            return "wrong_password";
////        }
////        System.out.println("success");
////        return "success";
//
//
//        Member member = memberRepository.findByName(name);
//        if(member != null){
//            if (!passwordEncoder.matches(password, member.getPassword())){
//                return "wrong_password";
//            } else return "success";
//        } else return "non_existent";
//
//
//    }
//
//    public String login(LoginDto dto) {
////        Member member = memberRepository.findByName(dto.getName())
////                .orElseThrow(() -> new IllegalArgumentException("일치하는 사용자가 없습니다. name: " + dto.getName()));
////        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
////            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
////        }
////
//////        Member member = memberRepository.findByName(dto.getName()).get();
////
//
//
//        Member member = memberRepository.findByName(dto.getName());
//        if(member == null || !passwordEncoder.matches(dto.getPassword(), member.getPassword())){
//            return "false";
//        } else return JwtUtil.createToken(member.getName(), secretKey, expireTimeMs);
//    }
//
//
//    public Long findMemberId(String name) {
//        return memberRepository.findByName(name).getId();
//    }
//

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(username)
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
