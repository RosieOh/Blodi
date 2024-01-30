package com.blodi.repository;

import com.blodi.entity.Member;
import com.blodi.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("select p from Profile p where p.member =:member")
    Profile ProfileByMember(Member member);
}
