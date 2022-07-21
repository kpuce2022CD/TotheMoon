package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByLoginId(String loginId);

    Optional<Member> findById(Long id);

    //아이디 중복 검사.
    boolean existsByLoginId(String loginId);
}
