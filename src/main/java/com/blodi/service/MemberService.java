package com.blodi.service;

import com.blodi.dto.MemberJoinDTO;
import com.blodi.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;;

public interface MemberService {
    void createAdminMember();

    static class MidExistException extends Exception {}
    Member existByEmail(String email);
    void join(MemberJoinDTO memberJoinDTO) ;

    void changePw(MemberJoinDTO memberJoinDTO);
    public PasswordEncoder passwordEncoder();



}