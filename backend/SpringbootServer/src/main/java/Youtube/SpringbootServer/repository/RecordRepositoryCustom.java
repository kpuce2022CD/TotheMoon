package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.RecordDTO;
import Youtube.SpringbootServer.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepositoryCustom {

    List<Record> search(@Param("id") long id);

    Page<RecordDTO> searchPage(@Param("id") long id, Pageable pageable);
}
