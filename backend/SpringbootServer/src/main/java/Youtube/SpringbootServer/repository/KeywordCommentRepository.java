package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.KeywordComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KeywordCommentRepository extends JpaRepository<KeywordComment, Long> {

    @Query("select c from KeywordComment c join fetch c.keyword k where k.record.id= :id")
    List<KeywordComment> findKeywordComments(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "delete from keyword_comment where keyword_id in (select keyword_id from keyword k where k.record_id= :id);", nativeQuery = true)
    void deleteKeywordComments(@Param("id") long id);
}
