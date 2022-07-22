package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Interest;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CommentDTO {

    private String index;
    private String id;  //작성자 id.
    private String comment;
    private String date;
    private String num_like;

    //DTO -> Entity
    public Comment toEntity(){
        return new Comment(id, date, index, comment, num_like);
    }

    //Response DTO
    @Getter
    public static class Response{

        private String commentIndex;
        private String commentUserId;
        private String content;
        private String commentLike;

        //Entity -> DTO
        public Response(Comment comment){
            this.commentIndex = comment.getCommentIndex();
            this.commentUserId = comment.getCommentUserId();
            this.content = comment.getContent();
            this.commentLike = comment.getCommentLike();
        }
    }


}
