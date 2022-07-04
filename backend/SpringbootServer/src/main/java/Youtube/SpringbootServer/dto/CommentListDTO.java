package Youtube.SpringbootServer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Getter
@Setter
@Component
public class CommentListDTO {

    private CommentDTO comments[];
}
