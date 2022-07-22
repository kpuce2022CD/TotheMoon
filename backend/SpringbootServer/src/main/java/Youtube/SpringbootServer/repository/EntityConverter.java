package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.*;
import Youtube.SpringbootServer.entity.*;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {

//    public Comment toCommentEntity(CommentDTO commentDTO){
//        return new Comment(commentDTO.getId(), commentDTO.getDate(), commentDTO.getIndex() , commentDTO.getComment(), commentDTO.getNum_like());
//    }

    public Keyword toKeywordEntity(KeywordDTO keywordDTO, int i){
        return new Keyword(i, keywordDTO.getB5()[i]);
    }

    public KeywordComment toKeywordCommentEntity(KeywordDTO keywordDTO,int i, int j){
        return new KeywordComment(keywordDTO.getComments()[i][j]);
    }

    public Percent toPercentEntity(PercentDTO percentDTO){
        return new Percent(percentDTO.getPositive(), percentDTO.getNegative(), percentDTO.getHappy(), percentDTO.getSurprise(), percentDTO.getAnger(), percentDTO.getSadness(),
                percentDTO.getNeutral(),percentDTO.getDisgust(), percentDTO.getFear());
    }

    public VideoInformation toVideoInfoEntity(VideoInformationDTO viDTO){
        return new VideoInformation(viDTO.getTitle(), viDTO.getDate(), viDTO.getView(), viDTO.getLike());
    }

//    public Interest toInterestEntity(InterestDTO interestDTO){
//        return new Interest(interestDTO.getCommentDate(),interestDTO.getCommentCount());
//    }

    public Timeline toTimelineEntity(TimelineDTO timelineDTO){
        return new Timeline(timelineDTO.getCount(),timelineDTO.getSec(),timelineDTO.getLabel());
    }

    public Member toMemberEntity(MemberDTO memberDTO){
        return new Member(memberDTO.getLoginId(), memberDTO.getPassword());
    }
}
