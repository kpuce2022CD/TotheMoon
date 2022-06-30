package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Percent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PercentRepository extends JpaRepository<Percent,Long> {

    @Query("select p from Percent p where p.record.id= :id")
    Percent findPercents(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from Percent p where p.record.id = :id")
    void deletePercents(@Param("id") long id);
}
