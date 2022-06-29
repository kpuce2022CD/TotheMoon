package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.KeywordComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordCommentRepository extends JpaRepository<KeywordComment, Long> {
}
