package com.shotny.bucketsharing.domain.buckets;

import com.shotny.bucketsharing.domain.BucketMembers;
import com.shotny.bucketsharing.domain.items.Items;
import com.shotny.bucketsharing.domain.members.Members;
import lombok.Builder;
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
    private String name;

    @Column
    private int itemCount;

    @Column
    private int checked;

    @Column
    private int memberCount;

    @OneToMany(mappedBy = "bucket")
    private List<Items> itemsList;

//    @OneToOne
    @Column
    private Members owner;

    @ManyToOne
    @JoinColumn(name = "bucketId")
    private BucketMembers bucketMembers;

    @Builder
    public Buckets(String name) {
        this.name = name;
    }

    public void setBucketMembers(BucketMembers bucketMember) {
        this.bucketMembers = bucketMember;
    }

    public void setOwner(Members member) {
        this.owner = member;
        ++this.memberCount;
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
