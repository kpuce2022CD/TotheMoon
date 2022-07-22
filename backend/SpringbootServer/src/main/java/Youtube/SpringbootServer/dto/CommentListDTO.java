package Youtube.SpringbootServer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CommentListDTO {

    private CommentDTO[] comments;

    private PercentDTO percent;

    public void setCommentListDTO(CommentDTO[] comments, PercentDTO percent) {
        this.comments = comments;
        this.percent = percent;
    }
}
