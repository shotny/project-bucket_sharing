package com.shotny.bucketsharing.domain.member.dto;

import com.shotny.bucketsharing.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSaveDto {
    private String name;
    private String password;

    public void setEncodePassword(String encoded) {
        this.password = encoded;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .build();
    }
}
