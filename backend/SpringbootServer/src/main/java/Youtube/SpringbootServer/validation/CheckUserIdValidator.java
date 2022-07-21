package Youtube.SpringbootServer.validation;

import Youtube.SpringbootServer.dto.MemberDTO;
import Youtube.SpringbootServer.entity.Member;
import Youtube.SpringbootServer.repository.EntityConverter;
import Youtube.SpringbootServer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class CheckUserIdValidator extends AbstractValidator<MemberDTO>{

    private final EntityConverter entityConverter;
    private final MemberRepository memberRepository;

    @Override
    protected void doValidate(MemberDTO memberDto, Errors errors) {
        Member member = entityConverter.toMemberEntity(memberDto);

        if (memberRepository.existsByLoginId(member.getLoginId())) {
            errors.rejectValue("loginId", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }
}
