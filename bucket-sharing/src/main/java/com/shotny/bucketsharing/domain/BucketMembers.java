package com.shotny.bucketsharing.domain;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BucketMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bucket_id")
    private Buckets bucket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public BucketMembers(Member member, Buckets bucket) {
        this.member = member;
        this.bucket = bucket;
    }
}
