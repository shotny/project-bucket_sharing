package com.shotny.bucketsharing.domain.members;

import com.shotny.bucketsharing.domain.BucketMembers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int bucketsCount;

    @OneToMany(mappedBy = "members")
    private List<BucketMembers> bucketMembers = new ArrayList<>();

    public Members(String name) {
        this.name = name;
    }

    public void updateBucket(boolean isCountUp) {
        this.bucketsCount = isCountUp ? ++this.bucketsCount : --bucketsCount;
    }
}
