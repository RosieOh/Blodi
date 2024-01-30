package com.blodi.service;

import com.blodi.entity.enums.MemberRole;
import com.blodi.dto.MemberJoinDTO;
import com.blodi.entity.Member;
import com.blodi.repository.MemberRepository;
import com.blodi.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final ProfileRepository profileRepository;

    @Override
    public void join(MemberJoinDTO memberJoinDTO) {

        Member member = modelMapper.map(memberJoinDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        member.addRole(MemberRole.USER);
        log.info("=======================");
        log.info(member);
        log.info(member.getRoleSet());
        memberRepository.save(member);
    }

    @Override
    public void createAdminMember() {
        // 이미 존재하는 회원인지 확인
        if (!memberRepository.existsByEmail("admin@naver.com")) {
            // 관리자 계정 생성 및 초기 설정
            Member admin = Member.builder()
                    .mpw(passwordEncoder.encode("1234"))
                    .mname("Admin")
                    .nickname("관리자")
                    .email("admin@naver.com")
                    .active(1)
                    .roleSet(Collections.singleton(MemberRole.ADMIN))
                    .role(MemberRole.valueOf("ADMIN"))
                    .build();

            memberRepository.save(admin);

            log.info("Admin 계정이 생성되었습니다.");
        } else {
            log.info("Admin 계정이 이미 존재합니다.");
        }
    }

    @Override
    public Member existByEmail(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    @Override
    public void changePw(MemberJoinDTO memberJoinDTO) {

        Optional<Member> result = memberRepository.findById(String.valueOf(memberJoinDTO.getMid()));
        Member member = result.orElseThrow();
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw()));
        memberRepository.save(member);
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}
