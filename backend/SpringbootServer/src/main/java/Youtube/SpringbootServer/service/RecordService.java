package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    @Transactional
    public void record(CommentDTO commentDTO){
        recordRepository.save(commentDTO);
    }
}
