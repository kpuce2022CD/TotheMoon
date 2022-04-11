package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.domain.Interest;
import org.springframework.stereotype.Service;

@Service
public class InterestService {

    //댓글 날짜
    public String[] countDate(Interest[] interests){
        String[] commentDate = new String[interests.length];

        for(int i=0;i< interests.length;i++){
            commentDate[i]=interests[i].getCommentDate();
        }
        return commentDate;
    }

    //댓글 갯수
    public String[] countComment(Interest[] interests){
        String[] commentCount = new String[interests.length];

        for(int i=0;i< interests.length;i++){
            commentCount[i]=interests[i].getCommentCount();
        }
        return commentCount;
    }
}