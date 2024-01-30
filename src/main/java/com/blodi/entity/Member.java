package com.blodi.entity;


import com.blodi.entity.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;

    private String mpw;

    private String mname;

    private String nickname;

    private String school;

    @Column(unique = true)
    private String email;

    private int active;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberRole role = MemberRole.USER; // 디폴트로 USER 권한을 갖도록 초기화

    @OneToOne(mappedBy = "member")
    private Profile profile;

    public void changePassword(String mpw) {
        this.mpw = mpw;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
    }
}
