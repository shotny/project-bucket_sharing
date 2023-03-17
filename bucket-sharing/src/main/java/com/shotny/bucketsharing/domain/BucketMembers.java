package com.shotny.bucketsharing.domain;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.members.Members;
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

    @ManyToOne
    @JoinColumn(name = "bucket_id")
    private Buckets bucket;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members members;

    public BucketMembers(Members member, Buckets bucket) {
        this.members = member;
        this.bucket = bucket;
    }
}
