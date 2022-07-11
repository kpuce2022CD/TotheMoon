package Youtube.SpringbootServer.Login;

import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){

        try{
            Member findMember = memberRepository.findByLoginId(loginId);
            if(findMember.getPassword().equals(password)){
                return findMember;
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }

    }
}
