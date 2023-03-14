package com.shotny.bucketsharing.domain.items;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import lombok.Builder;
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

    @ManyToOne
    @JoinColumn(name = "bucketId")
    private Buckets bucket;

    @Builder
    public Items(String content) {
        this.content = content;
    }

    public void setBucket(Buckets bucket) {
        this.bucket = bucket;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public boolean updateCheck() {
        check = check ? false : true;
//        if (check) {
//            check = false;
//        } else check = true;
        return check;
    }
}
