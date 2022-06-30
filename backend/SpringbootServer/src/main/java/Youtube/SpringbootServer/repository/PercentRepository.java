package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Percent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PercentRepository extends JpaRepository<Percent,Long> {
}
