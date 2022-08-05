package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.SessionConst;
import Youtube.SpringbootServer.dto.MemberDTO;
import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.service.MemberService;
import Youtube.SpringbootServer.validation.CheckUserIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CheckUserIdValidator checkUserIdValidator;

    //커스텀 유효성을 검증하기 위해 추가
    @InitBinder
    public void validatorBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(checkUserIdValidator);
    }

    //회원가입
    @GetMapping("/members/new")
    public String addForm(@ModelAttribute("memberDTO") MemberDTO memberDTO) {
        return "login/addMemberForm";
    }

    //회원가입
    @PostMapping("/members/new")
    public String save(@Valid @ModelAttribute MemberDTO memberDTO, BindingResult result) throws IOException {

        //검증 오류 시
        if (result.hasErrors()) {
            return "login/addMemberForm";
        }
        //회원 등록
        memberService.registerDB(memberDTO);

        return "redirect:/login";
    }

    //마이페이지
    @GetMapping("/my-page")
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
