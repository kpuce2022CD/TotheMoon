package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select c from Comment c where c.record.id= :id")
    List<Comment> findComments(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.record.id = :id")
    void deleteComments(@Param("id") long id);
}
