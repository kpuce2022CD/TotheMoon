package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.RecordDTO;
import Youtube.SpringbootServer.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepositoryCustom {

//    List<Record> search(@Param("id") long id);

    //리스트 조회 (검색+페이징기능)
    Page<RecordDTO> searchPage(@Param("id") long id, Pageable pageable, String searchText);
}
