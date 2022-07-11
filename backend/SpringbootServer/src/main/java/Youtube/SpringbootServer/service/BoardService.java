package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.*;
import Youtube.SpringbootServer.entity.*;
import Youtube.SpringbootServer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final CommentRepository commentRepository;
    private final RecordRepository recordRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordCommentRepository keywordCommentRepository;
    private final PercentRepository percentRepository;
    private final VideoInfoRepository videoInfoRepository;
    private final InterestRepository interestRepository;
    private final TimeLineRepository timeLineRepository;
    private final EntityConverter entityConverter;

//    //분석결과 등록(순수JPA)
//    @Transactional
//    public void record(CommentListDTO commentListDTO, Record record){
//        recordRepository.save(commentListDTO, record);
//    }

    //분석 결과 등록(스프링데이터 JPA)
    public void registerDB(CommentListDTO commentListDTO, Record record, KeywordDTO keywordDTO, PercentDTO percentDTO,
                           VideoInformationDTO videoInformationDTO, InterestListDTO interestListDTO, TimeLineListDTO timelineListDTO){

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

        //percent 저장
        Percent percent = entityConverter.toPercentEntity(percentDTO);
        percent.setRecord(record);
        percentRepository.save(percent);

        //videoInformation 저장.
        VideoInformation videoInformation = entityConverter.toVideoInfoEntity(videoInformationDTO);
        videoInformation.setRecord(record);
        videoInfoRepository.save(videoInformation);

        //interest 저장
        InterestDTO[] interests = interestListDTO.getInterests();
        for (InterestDTO interestDTO : interests) {
            Interest interest = entityConverter.toInterestEntity(interestDTO); //InterestDTO -> interest 엔티티로 변환.
            interest.setRecord(record);
            interestRepository.save(interest);
        }

        //타임라인 저장.
        TimelineDTO[] timelines = timelineListDTO.getTimeline();
        for (TimelineDTO timelineDTO : timelines) {
            Timeline timeline = entityConverter.toTimelineEntity(timelineDTO);  //TimelineDTO -> timeline 엔티티로 변환.
            timeline.setRecord(record);
            timeLineRepository.save(timeline);
        }
    }


    //분석 리스트 출력.
    public List<Record> findRecords(Long id){
        return recordRepository.findByMemberId(id);
    }

    //분석 1건 comment 조회
    public List<Comment> findComment(Long recordId){
        return commentRepository.findComments(recordId);
    }

    public List<Interest> findInterest(Long recordId){
        return interestRepository.findInterests(recordId);
    }

    public List<Keyword> findKeyword(Long recordId){
        return keywordRepository.findKeywords(recordId);
    }

    public Percent findPercent(Long recordId){
        return percentRepository.findPercents(recordId);
    }

    public List<Timeline> findTimeLine(Long recordId){
        return timeLineRepository.findTimelines(recordId);
    }

    public VideoInformation findVideoInfo(Long recordId){
        return videoInfoRepository.findVideoInfo(recordId);
    }

    public List<KeywordComment> findKeywordComment(Long recordId){
        return keywordCommentRepository.findKeywordComments(recordId);
    }

    //분석 1건 삭제
    public void delete(Long recordId){
        commentRepository.deleteComments(recordId);
        interestRepository.deleteInterests(recordId);
        percentRepository.deletePercents(recordId);
        timeLineRepository.deleteTimelines(recordId);
        videoInfoRepository.deleteVideoInfo(recordId);
        keywordCommentRepository.deleteKeywordComments(recordId);
        keywordRepository.deleteKeywords(recordId);
        recordRepository.deleteById(recordId);
    }

}
