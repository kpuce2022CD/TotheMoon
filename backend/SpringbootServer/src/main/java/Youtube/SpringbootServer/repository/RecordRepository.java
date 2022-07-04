package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RecordRepository extends JpaRepository<Record,Long> {

    @Modifying
    @Transactional
    void deleteById(long id);
}
