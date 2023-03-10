package com.shotny.bucketsharing.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class Buckets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int itemCount;

    @Column
    private int checked;

    @Column
    private int memberCount;

    @Column
    private List<Items> itemsList;
}
