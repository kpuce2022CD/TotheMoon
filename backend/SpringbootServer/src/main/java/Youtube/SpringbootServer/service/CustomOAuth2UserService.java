package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.SessionConst;
import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


//    private final UserRepository userRepository;
    private final MemberRepository memberRepository;


    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {



        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 서비스 구분을 위한 작업 (구글: google, 네이버: naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        String email;
        Map<String, Object> response = oAuth2User.getAttributes();

        if(registrationId.equals("naver")){
            Map<String, Object> hash = (Map<String, Object>) response.get("response");

            email = (String) hash.get("email");
        }else if(registrationId.equals("google")){
            email = (String) response.get("email");
        }else{
            throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        Member member;
        Optional<Member> optionalUser = Optional.ofNullable(memberRepository.findByLoginId(email));

        // 이미 가입한 사람일 경우
        if(optionalUser.isPresent()){
            member = optionalUser.get();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        }else { // 처음일 경우
            member = new Member();
            member.setLoginId(email);
//            user.setRole(Role.ROLE_USER);
            memberRepository.save(member);
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        }
//        httpSession.setAttribute("user", new SessionUser(user));


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
                , oAuth2User.getAttributes()
                , userNameAttributeName);

    }
}
