package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {

    public Comment toCommentEntity(CommentDTO commentDTO){
        return new Comment(commentDTO.getId(), commentDTO.getDate(), commentDTO.getIndex() , commentDTO.getComment(), commentDTO.getNum_like());
    }
}
