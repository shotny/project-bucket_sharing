package com.shotny.bucketsharing.domain.buckets;

import com.shotny.bucketsharing.domain.BucketMembers;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.members.Members;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Buckets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long ownerId;

    @Column
    private int itemCount;

    @Column
    private int checked;

    @Column
    private int memberCount;

    @OneToMany(mappedBy = "bucket")
    private List<Items> itemsList;

    @OneToMany(mappedBy = "bucket")
    private List<BucketMembers> bucketMembers = new ArrayList<>();


    @Builder
    public Buckets(String name, Long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
        this.memberCount = 1;
    }

    public void updateMemberCount(boolean countUp) {
        this.memberCount = countUp ? ++this.memberCount : --this.memberCount;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void countUpItem() {
        ++itemCount;
    }

    public void countDownItem() {
        if(itemCount != 0) --itemCount;
    }

    public void updateChecked(boolean check) {
        this.checked = check ? ++this.checked : --this.checked;
    }
}
