package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Percent;
import Youtube.SpringbootServer.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,Long>, RecordRepositoryCustom {

    @Modifying
    @Transactional
    void deleteById(long id);

    List<Record> findByMemberId(long id);

    @Query("select r from Record r join fetch r.videoInformation where r.member.id= :id")
    List<Record> findRecords(@Param("id") long id);
}
