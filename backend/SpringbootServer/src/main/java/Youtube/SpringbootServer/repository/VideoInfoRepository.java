package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.VideoInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoInfoRepository extends JpaRepository<VideoInformation,Long> {
}
