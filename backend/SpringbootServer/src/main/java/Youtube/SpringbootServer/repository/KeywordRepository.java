package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {

    @Query("select k from Keyword k where k.record.id= :id")
    List<Keyword> findKeywords(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Keyword k where k.record.id = :id")
    void deleteKeywords(@Param("id") long id);
}
