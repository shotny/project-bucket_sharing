package com.shotny.bucketsharing.domain;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import com.shotny.bucketsharing.domain.members.Members;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class BucketMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column
//    private int memberCount;

    @OneToMany(mappedBy = "bucketMembers")
    private List<Buckets> bucket = new ArrayList<>();

    @OneToMany(mappedBy = "bucketMembers")
    private List<Members> members = new ArrayList<>();
}
