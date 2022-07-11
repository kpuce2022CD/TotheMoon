package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByLoginId(String loginId);
}
