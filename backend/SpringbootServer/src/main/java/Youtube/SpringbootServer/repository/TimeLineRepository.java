package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLineRepository extends JpaRepository<Timeline, Long> {
}
