package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.dto.CommentListDTO;
import Youtube.SpringbootServer.dto.KeywordDTO;
import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.Keyword;
import Youtube.SpringbootServer.entity.KeywordComment;
import Youtube.SpringbootServer.entity.Record;
import Youtube.SpringbootServer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final CommentRepository commentRepository;
    private final RecordRepository recordRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordCommentRepository keywordCommentRepository;
    private final EntityConverter entityConverter;

//    //분석결과 등록(순수JPA)
//    @Transactional
//    public void record(CommentListDTO commentListDTO, Record record){
//        recordRepository.save(commentListDTO, record);
//    }

    //분석 결과 등록(스프링데이터 JPA)
    public void registerDB(CommentListDTO commentListDTO, Record record, KeywordDTO keywordDTO){

        //record 저장
        recordRepository.save(record);

        //comment 저장.
        CommentDTO[] commentDTOs = commentListDTO.getComments();
        for (CommentDTO commentDTO : commentDTOs) {
            Comment comment = entityConverter.toCommentEntity(commentDTO);  //CommentDTO -> comment엔티티로 변환
            comment.setRecord(record);
            commentRepository.save(comment);
        }

        //keyword 저장,
        for(int i=0; i<keywordDTO.getComments().length; i++){
            Keyword keyword = entityConverter.toKeywordEntity(keywordDTO, i);  //keyDTO -> keyword 엔티티로 변환.
            keyword.setRecord(record);
            keywordRepository.save(keyword);

            //keywordComment 저장.
            for(int j=0; j< keywordDTO.getComments()[i].length; j++) {
                KeywordComment keywordComment = entityConverter.toKeywordCommentEntity(keywordDTO, i,j);//keyDTO -> keywordComment 엔티티로 변환
                keywordComment.setKeyword(keyword);
                keywordCommentRepository.save(keywordComment);
            }
        }
    }


    //분석 리스트 출력.
    public List<Record> findRecords(){
        return recordRepository.findAll();
    }

    //분석 1건 조회
    public List<Comment> findComment(Long recordId){
        return commentRepository.findComments(recordId);
    }

    //분석 1건 삭제
    public void delete(Long recordId){
        commentRepository.deleteComments(recordId);
        recordRepository.deleteById(recordId);
    }

}
