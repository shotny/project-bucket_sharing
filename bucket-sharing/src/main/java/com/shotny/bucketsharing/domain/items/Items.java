package com.shotny.bucketsharing.domain.items;

import com.shotny.bucketsharing.domain.buckets.Buckets;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private boolean checked;

    @ManyToOne
    @JoinColumn(name = "bucketId")
    private Buckets bucket;


    @Builder
    public Items(String content, Buckets bucket) {
        this.content = content;
        this.bucket = bucket;
    }

//    public void setBucket(Buckets bucket) {
//        this.bucket = bucket;
//    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateCheck() {
        this.checked = this.checked ? false : true;
    }
}
