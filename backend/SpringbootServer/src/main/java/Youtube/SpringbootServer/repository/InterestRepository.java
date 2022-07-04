package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest,Long> {

    @Query("select i from Interest i where i.record.id= :id")
    List<Interest> findInterests(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Interest i where i.record.id = :id")
    void deleteInterests(@Param("id") long id);
}
