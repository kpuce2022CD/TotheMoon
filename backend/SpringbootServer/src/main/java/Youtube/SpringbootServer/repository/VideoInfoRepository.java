package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.VideoInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoInfoRepository extends JpaRepository<VideoInformation,Long> {

    @Query("select v from VideoInformation v where v.record.id= :id")
    VideoInformation findVideoInfo(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("delete from VideoInformation v where v.record.id = :id")
    void deleteVideoInfo(@Param("id") long id);

}
