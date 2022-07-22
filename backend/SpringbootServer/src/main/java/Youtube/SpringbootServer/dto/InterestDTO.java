package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Interest;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
public class InterestDTO {

    private String commentDate;
    private String commentCount;

    //DTO->Entity
    public Interest toEntity(){     //DTO -> 엔티티
        return new Interest(commentDate,commentCount);
    }

    //Response DTO
    @Getter
    public static class Response{

        private String commentDate;
        private String commentCount;

        //Entity -> DTO
        public Response(Interest interest){
            this.commentDate = interest.getCommentDate();
            this.commentCount = interest.getCommentCount();
        }
    }

}
