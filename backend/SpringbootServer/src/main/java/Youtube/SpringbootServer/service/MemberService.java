package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.MemberDTO;
import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.repository.EntityConverter;
import Youtube.SpringbootServer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EntityConverter entityConverter;

    public void registerDB(MemberDTO memberDTO){
        Member member = entityConverter.toMemberEntity(memberDTO);   //memberDto -> member로 변환.
        memberRepository.save(member);
    }
}
