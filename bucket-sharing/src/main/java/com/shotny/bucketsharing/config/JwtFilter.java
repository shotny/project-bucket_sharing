package com.shotny.bucketsharing.config;

import com.shotny.bucketsharing.domain.member.MemberService;
import com.shotny.bucketsharing.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final String secretKey;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // header _ getToken
//        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//        log.info("authorization: {}", authorization);


        // without Token -> BLOCK
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            log.info("잘못된 authorization 을 보냈습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }

        // delete Bearer from Token
//        String token = authorization.split(" ")[1];



        // get Token - cookie
//        final String access = request.getHeader("access");
        String token = "";
        Cookie[] cookies = request.getCookies();
        boolean haveToken = false;
        if (cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    haveToken = true;
                    token = URLDecoder.decode(cookie.getValue(), "UTF-8");
                }
            }
        }

        // without Token -> BLOCK
        if (!haveToken) {
            log.info("잘못된 authorization 을 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // Validate Expired Token
        if (JwtUtil.isExpired(token, secretKey)) {
            log.info("Token 이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }


        // Token _ get Username
        String username = JwtUtil.getUsername(token, secretKey);
        log.info("username: {}", username);


        // auth
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("USER")));


        // details
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
