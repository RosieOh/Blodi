package com.blodi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pno;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mid")
    private Member member;

    @Builder.Default
    private String memberImg = "basic.png";

    private String intro;

    private String gitLink;

    private String instaLink;

    private String blogLink;

    public void change(String intro, String gitLink, String blogLink, String instaLink) {
        this.intro = intro;
        this.gitLink = gitLink;
        this.instaLink = instaLink;
        this.blogLink = blogLink;
    }
}
