package com.blodi.component;

import com.blodi.entity.enums.MemberRole;
import com.blodi.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.blodi.entity.Member;
import org.springframework.core.env.Environment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AdminAccountComponent implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment environment;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // 이전에 실행되었는지 확인
        if (Boolean.parseBoolean(environment.getProperty("admin.account.run-once"))) {
            return;
        }

        // 기본 값으로 Admin 계정이 등록되도록 설정
        // 관리자 계정 추가
        Member admin = Member.builder()
                .mpw(passwordEncoder.encode("1234"))
                .mname("Admin")
                .nickname("관리자")
                .email("admin@naver.com")
                .active(1)
                .roleSet(Collections.singleton(MemberRole.ADMIN)) // 관리자 권한 추가
                .build();

        memberRepository.save(admin);

        Member teacher = Member.builder()
                .mpw(passwordEncoder.encode("1234"))
                .mname("Teacher")
                .nickname("선생님1")
                .email("dhxogns920@gmail.com")
                .active(1)
                .roleSet(Collections.singleton(MemberRole.TEACHER)) // 선생님 권한 추가
                .build();

        memberRepository.save(teacher);

        Member user = Member.builder()
                .mpw(passwordEncoder.encode("1234"))
                .mname("학생1")
                .nickname("학생1")
                .email("20172207@gm.hannam.ac.kr")
                .active(1)
                .roleSet(Collections.singleton(MemberRole.USER)) // 학생 권한 추가
                .build();

        memberRepository.save(user);

        // 이게 properties에서 설정한 걸 토대로 처음 한번만 입력해주는 거
        System.setProperty("admin.account.run-once", "true");
        entityManager.flush();
    }
}
