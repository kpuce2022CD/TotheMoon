package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityConverter entityConverter;
    private final EntityManager em;

    /** 댓글 분석 저장하기 **/
    public void save(CommentDTO commentDTO){
        Comment comment = entityConverter.toCommentEntity(commentDTO);
        em.persist(comment);
    }

}
