package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.dto.KeywordDTO;
import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Keyword;
import Youtube.SpringbootServer.entity.KeywordComment;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {

    public Comment toCommentEntity(CommentDTO commentDTO){
        return new Comment(commentDTO.getId(), commentDTO.getDate(), commentDTO.getIndex() , commentDTO.getComment(), commentDTO.getNum_like());
    }

    public Keyword toKeywordEntity(KeywordDTO keywordDTO, int i){
        return new Keyword(i, keywordDTO.getB5()[i]);
    }

    public KeywordComment toKeywordCommentEntity(KeywordDTO keywordDTO,int i, int j){
        return new KeywordComment(keywordDTO.getComments()[i][j]);
    }
}
