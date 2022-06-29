package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {
}
