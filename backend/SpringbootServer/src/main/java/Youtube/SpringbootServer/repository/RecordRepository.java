package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Long> {

    @Modifying
    @Transactional
    void deleteById(long id);

    List<Record> findByMemberId(long id);


}
