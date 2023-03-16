package com.shotny.bucketsharing.domain.members;

import com.shotny.bucketsharing.domain.BucketMembers;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int bucketsCount;

    @ManyToOne
    @JoinColumn(name = "bucketMembersId")
    private BucketMembers bucketMembers;

    public void setBucketMembers(BucketMembers bucketMembers) {
        this.bucketMembers = bucketMembers;
    }
}
