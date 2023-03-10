package com.shotny.bucketsharing.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class BucketMembers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Members owner;

    @Column
    private Buckets buckets;

    @Column
    private List<Members> members;
}
