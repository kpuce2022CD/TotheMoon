package Youtube.SpringbootServer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@Component
public class CommentListDTO {

    private CommentDTO comments[];

    private PercentDTO percent;

    public void setCommentListDTO(CommentDTO[] comments, PercentDTO percent) {
        this.comments = comments;
        this.percent = percent;
    }
}
