package com.shotny.bucketsharing.domain.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shotny.bucketsharing.domain.BucketMembers.BucketMembers;
import com.shotny.bucketsharing.token.Token;
import com.shotny.bucketsharing.token.TokenRepository;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column//(unique = true, nullable = false, length = 50)
    private String name;

    @Column
    private String password;

    @Column
    private int bucketsCount;

    @OneToMany(mappedBy = "member")
    private List<BucketMembers> bucketMembers = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Token token;

    public Member(String name) {
        this.name = name;
    }

    @Builder
    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Member updateBucket(boolean isCountUp) {
        this.bucketsCount = isCountUp ? ++this.bucketsCount : --bucketsCount;
        return this;
    }


}
