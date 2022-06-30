package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest,Long> {
}
