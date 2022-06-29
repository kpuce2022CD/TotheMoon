package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.dto.CommentListDTO;
import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityConverter entityConverter;
    private final EntityManager em;

    /** 댓글 분석 저장하기 **/
    public void save(CommentListDTO commentListDTO, Record record){

        em.persist(record);

        CommentDTO[] commentDTOs = commentListDTO.getComments();
        //comment 저장.
        for (CommentDTO commentDTO : commentDTOs) {
            Comment comment = entityConverter.toCommentEntity(commentDTO);
            comment.setRecord(record);
            em.persist(comment);
        }
    }

    /**분석 리스트 조회하기**/
    public List<Record> findALL(){
        return em.createQuery("select r from Record r", Record.class).getResultList();
    }

    /**record id로 1건 분석 조회**/
    public List<Comment> findCommentList(Long id){
        return em.createQuery("select c from Comment c where c.record.id = :record",Comment.class)
                .setParameter("record",id)
                .getResultList();
    }

    /**record id로 1건 분석 삭제**/
    public void delete(long id){
        em.createQuery("delete from Comment c where c.record.id = :record")
                .setParameter("record", id)
                .executeUpdate();

        em.createQuery("delete from Record r where r.id = :record")
                .setParameter("record", id)
                .executeUpdate();
    }

}
