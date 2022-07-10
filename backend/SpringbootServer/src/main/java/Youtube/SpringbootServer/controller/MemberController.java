package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.SessionConst;
import Youtube.SpringbootServer.dto.MemberDTO;
import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.repository.MemberRepository;
import Youtube.SpringbootServer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/members/add")
    public String addForm(@ModelAttribute("memberDTO") MemberDTO memberDTO) {
        return "login/addMemberForm";
    }

    @PostMapping("/members/add")
    public String save(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult result, HttpServletResponse response) throws IOException {

        if (result.hasErrors()) {
            return "login/addMemberForm";
        }
        memberService.registerDB(memberDTO);

        return "redirect:/login";
    }

    @GetMapping("/memberinfo")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면(로그아웃 상태) 로그인 페이지로
        if (loginMember == null) {
            return "redirect:/login";
        }

        //세션이 유지되면(로그인된 상태) 마이페이지 이동.
        model.addAttribute("member", loginMember);
        return "login/memberInfo";
    }

}
