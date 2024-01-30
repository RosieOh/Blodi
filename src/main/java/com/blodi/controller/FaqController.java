package com.blodi.controller;

import com.blodi.dto.BoardDTO;
import com.blodi.entity.Member;
import com.blodi.repository.MemberRepository;
import com.blodi.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FaqController {

    private final BoardService boardService;

    private final MemberRepository memberRepository;

    // 리스트만 불러오고 테스트 DB로 해결하는 걸로
    @GetMapping({"/faq", "/faq/list"})
    public String FaqListAll(Model model, Principal principal) {
        String boardType = "FAQ";
        List<BoardDTO> boardList = boardService.findByBoardType(boardType);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("boardList", boardList);
        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        model.addAttribute("member", member);
        return "faq/list";
    }
}