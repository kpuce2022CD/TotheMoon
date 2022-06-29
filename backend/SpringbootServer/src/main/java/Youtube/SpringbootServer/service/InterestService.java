package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.InterestDTO;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

    //댓글 날짜
    public String[] countDate(InterestDTO[] interests){
        String[] commentDate = new String[interests.length];

        for(int i=0;i< interests.length;i++){
            commentDate[i]=interests[i].getCommentDate();
        }
        return commentDate;
    }

    //댓글 갯수
    public String[] countComment(InterestDTO[] interests){
        String[] commentCount = new String[interests.length];

        for(int i=0;i< interests.length;i++){
            commentCount[i]=interests[i].getCommentCount();
        }
        return commentCount;
    }
}