package com.shotny.bucketsharing.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

//    토큰 생성
    public static String createToken(String username, String secretKey, Long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
//        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }


    //토큰 유효성 확인_토큰의 유효시간이 현재보다 이전인지(만료되었는지?)
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }


    //토큰에서 유저네임 꺼내기
    public static String getUsername(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody().get("username", String.class);
    }
}
