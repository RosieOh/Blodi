package com.blodi.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// Spring Data JPA에서 감사 정보(생성자 및 수정자 정보)를 기록하기 위한 AuditorAware 구현체
public class AuditorAwareConfig implements AuditorAware<String> {

    // 현재 사용자의 ID를 반환하는 메서드
    @Override
    public Optional<String> getCurrentAuditor() {
        // Spring Security의 Authentication을 사용하여 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";

        // 인증 정보가 존재하면 사용자의 ID를 가져옴
        if (authentication != null) {
            userId = authentication.getName();
        }

        // Optional을 사용하여 반환
        return Optional.of(userId);
    }
}
