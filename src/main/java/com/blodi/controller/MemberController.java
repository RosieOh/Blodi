package com.blodi.controller;

import com.blodi.dto.MemberJoinDTO;
import com.blodi.entity.Member;
import com.blodi.entity.Profile;
import com.blodi.repository.MemberRepository;
import com.blodi.service.MemberService;
import com.blodi.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProfileService profileService;

    private final BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/member/login")
    public String login(Model model, Principal principal) {
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
        return "member/login";
    }

    @GetMapping("/member/loginFail")
    public String loginFail (Model model) {
        model.addAttribute("msg", "로그인 실패! 다시 시도해 주세요!");
        model.addAttribute("url", "login");
        return "layout/loginFail";
    }

    @GetMapping("/member/join")
    public String join(Model model) {
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
        return "member/join";
    }

    @PostMapping("/member/join")
    public String joinPOST(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult, Model model){
        log.info("join post...");
        log.info(memberJoinDTO);
        String email = memberJoinDTO.getEmail();
        Member existEmail = memberService.existByEmail(email);
        System.out.println(existEmail);
        if(existEmail!=null){
            bindingResult
                    .rejectValue("email", "error.email", "사용이 불가한 이메일입니다.");
        }

        if(!Objects.equals(memberJoinDTO.getPasswordConfirm(), memberJoinDTO.getMpw())){
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "비밀번호와 비밀번호 확인이 다릅니다.");
        }

        if(bindingResult.hasErrors()){
            System.out.println("error"+bindingResult.hasErrors());
            System.out.println("e" +bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("error", bindingResult.hasErrors());
            model.addAttribute("memberJoinDTO", memberJoinDTO);
            return "member/login";
        }

        memberService.join(memberJoinDTO);
        return "redirect:login";
    }

    @GetMapping("/member/mypage")
    public String mypageForm(Principal principal, Model model){
        log.info("/mypage .........");
        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        Profile profile = profileService.profileByMember(member);
        System.out.println(profile);
        model.addAttribute("member", member);
        model.addAttribute("profile", profile);
        return "member/mypage";
    }


    @PostMapping("/member/mypage")
    public String mypage (Profile profile, Principal principal){

        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        Profile exist = profileService.profileByMember(member);
        log.info(exist);
        Profile profile1 = new Profile();


        if(exist == null) {
            log.info("==============추가");
            profile1.setMember(member);
            profile1.setIntro(profile.getIntro());
            profile1.setGitLink(profile.getGitLink());
            if (profile.getInstaLink() != null) {
                profile1.setInstaLink(profile.getInstaLink());
            }
            profileService.insertProfile(profile1);
        }else {
            log.info("==============수정");
            profile1.setPno(exist.getPno());
            profile1.setIntro(profile.getIntro());
            profile1.setGitLink(profile.getGitLink());
            if (profile.getInstaLink() != null) {
                profile1.setGitLink(profile.getInstaLink());
            }
            profileService.updateProfile(profile1);

        }

        return "redirect:/member/mypage";
    }

    @GetMapping("/member/check")
    public String pwCheckForm (){
        return "/member/pwCheck";
    }

    @PostMapping("/member/check")
    public String pwCheck(@RequestParam("mpw")String mpw, Principal principal, Model model){
        Member member = memberRepository.findByMid(principal.getName());
        String pw = member.getMpw();

        if(passwordEncoder.matches(mpw, pw)){
            return "redirect:/member/memberUpdate";
        } else{
            model.addAttribute("msg", "비밀번호가 틀렸습니다. 다시 작성해주세요:)");
            model.addAttribute("url", "/member/check");
            return "layout/alert";
        }
    }

    @GetMapping("/member/memberUpdate")
    public String memberUpdateForm(Principal principal, Model model){
        String mid = principal.getName();
        Member member = memberRepository.findByMid(mid);
        String pw = "";
        for(int i=0; i<member.getMpw().length(); i++){
            pw += "*";
        }
        model.addAttribute("pw", pw);
        model.addAttribute("member", member);
        return "/member/memberEdit";
    }

    @GetMapping("/member/changePw")
    public String changPwForm(Model model){
        model.addAttribute("memberJoinDTO", new MemberJoinDTO());
        return "/member/changePw";
    }

    @PostMapping("/member/changePw")
    public String changePw(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult,Principal principal, Model model){

        if(memberJoinDTO.getPasswordConfirm() != memberJoinDTO.getMpw())
            bindingResult.rejectValue("mpw", "error.mpw", "비밀번호와 비밀번호 확인이 다릅니다.");

        if(passwordEncoder.matches(memberJoinDTO.getNowPassword(), memberRepository.findByMid(principal.getName()).getMpw())){
            MemberJoinDTO mem = new MemberJoinDTO();
            mem.setMid(Integer.parseInt(principal.getName()));
            mem.setMpw(memberJoinDTO.getMpw());
            memberService.changePw(mem);
        }

        return "redirect:/member/mypage";
    }
}
