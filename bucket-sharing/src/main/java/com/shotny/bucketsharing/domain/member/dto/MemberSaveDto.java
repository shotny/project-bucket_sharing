package com.shotny.bucketsharing.domain.member.dto;

import com.shotny.bucketsharing.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSaveDto {
    private String name;
    private String password;
    private String passwordCheck;

    public void setEncodePassword(String encoded) {
        this.password = encoded;
        System.out.println("password = " + password);
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .build();
    }
}
