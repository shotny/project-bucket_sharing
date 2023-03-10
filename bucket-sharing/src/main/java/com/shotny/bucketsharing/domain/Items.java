package com.shotny.bucketsharing.domain;

import lombok.Generated;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private boolean check;

    @Column
    private Buckets buckets;
}
