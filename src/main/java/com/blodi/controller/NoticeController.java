package com.blodi.controller;

import com.blodi.dto.BoardDTO;
import com.blodi.dto.FileDTO;
import com.blodi.entity.Member;
import com.blodi.repository.MemberRepository;
import com.blodi.service.BoardService;
import com.blodi.service.FileService;
import com.blodi.util.MD5Generator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class NoticeController {
    @Value("${upload.path}")
    private String uploadPath;

    private final BoardService boardService;

    private final MemberRepository memberRepository;

    private final FileService fileService;

    @GetMapping({"/notice", "/notice/list"})
    public String boardListAll(Model model, Principal principal, BoardDTO boardDTO) {
        String boardType = "NOTICE";
        List<BoardDTO> boardList = boardService.findByBoardType(boardType);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("boardList", boardList);
        boardDTO.setWriter(boardDTO.getWriter());
        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        model.addAttribute("member", member);
        model.addAttribute("principal", principal);
        model.addAttribute("writer", principal);
        return "notice/list";
    }

    @GetMapping("/notice/read")
    public String readNotice(Long bno, Long id, Model model) {
        BoardDTO boardDTO = boardService.findByBno(bno);
        FileDTO fileDTO = fileService.getFile(boardDTO.getFileId());
        log.info(boardDTO);
        log.info(fileDTO.toString());
        model.addAttribute("boardList", boardDTO);
        model.addAttribute("fileList", fileDTO);
        return "notice/read";
    }


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/notice/register")
    public String registerForm(Model model, Principal principal) {
        model.addAttribute("principal", principal);
        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        model.addAttribute("writer", "admin");
        return "notice/register";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/notice/register")
    public String noticeRegister(@Valid BoardDTO boardDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam("file") MultipartFile files) {
        log.info("board POST register.......");
        log.info("이름 어디 갔노" + boardDTO.getWriter());
        if (bindingResult.hasErrors()) {
            log.info("has errors..........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        }
        try {
            String originFilename = files.getOriginalFilename();
            String filename = new MD5Generator(originFilename).toString();
            String savePath = System.getProperty("user.dir") + "/files/";
            log.info("어디로 가니?  " + savePath);
            if(!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdirs();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String filePath = savePath + filename;

            files.transferTo(new File(filePath));

            FileDTO fileDTO = new FileDTO();
            fileDTO.setOriginFilename(originFilename);
            fileDTO.setFilename(filename);
            fileDTO.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDTO);
            boardDTO.setFileId(fileId);
            boardDTO.setWriter(boardDTO.getWriter());
            boardService.register(boardDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/notice/list";
    }

    @GetMapping("/notice/modify")
    public String modifyForm(Long bno, Model model) {
        BoardDTO boardDTO = boardService.findByBno(bno);
        model.addAttribute("dto", boardDTO);
        return "notice/modify";
    }

    @PostMapping("/notice/modify")
    public String modify(@Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("bno", boardDTO.getBno());
        }

        boardService.modify(boardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/notice/read";
    }

    @RequestMapping(value = "/notice/remove", method = {RequestMethod.GET, RequestMethod.POST})
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("remove post.. " + bno);
        boardService.remove(bno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/notice/list";
    }

    private void removeFiles(List<String> files) {
        for (String fileName:files) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();
            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();
                if (contentType.startsWith("image")) {
                    File thumbnamilFile = new File(uploadPath + File.separator+"s_"+ fileName);
                    thumbnamilFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
