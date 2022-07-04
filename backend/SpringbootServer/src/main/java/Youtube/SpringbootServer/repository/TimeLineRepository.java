package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TimeLineRepository extends JpaRepository<Timeline, Long> {

    @Query("select t from Timeline t where t.record.id= :id")
    List<Timeline> findTimelines(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Timeline t where t.record.id = :id")
    void deleteTimelines(@Param("id") long id);
}
