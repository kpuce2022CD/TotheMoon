package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.SessionConst;
import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private MemberRepository memberRepository;

    @GetMapping("/home")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 로그아웃된 페이지
        if (loginMember == null) {
            return "redirect:http://localhost:3000/";
        }

        //세션이 유지되면 로그인된 페이지
        model.addAttribute("member", loginMember);
        return "redirect:http://localhost:3000/";
    }
}
