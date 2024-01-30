package com.blodi.service;

import com.blodi.entity.Member;
import com.blodi.entity.Profile;

public interface ProfileService {

    int insertProfile(Profile profile);

    void updateProfile(Profile profile);

    Profile profileByMember(Member member);
}
