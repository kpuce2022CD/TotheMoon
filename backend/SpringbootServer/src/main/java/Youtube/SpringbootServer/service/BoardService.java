package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.*;
import Youtube.SpringbootServer.entity.*;
import Youtube.SpringbootServer.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final MemberRepository memberRepository;

//    //분석결과 등록(순수JPA)
//    @Transactional
//    public void record(CommentListDTO commentListDTO, Record record){
//        recordRepository.save(commentListDTO, record);
//    }

    //분석 결과 등록(스프링데이터 JPA)
    @Transactional
    public void registerDB(CommentListDTO commentListDTO, Record record, Member loginMember, KeywordDTO keywordDTO,
                           KeywordCommentDTO keywordCommentDTO, PercentDTO percentDTO, VideoInformationDTO videoInformationDTO,
                           InterestListDTO interestListDTO, TimeLineListDTO timelineListDTO){

        //record 저장
        Member member = memberRepository.findById(loginMember.getId()).get();
        record.addMember(member);
        recordRepository.save(record);

        //comment 저장.
        CommentDTO[] commentDTOs = commentListDTO.getComments();
        for (CommentDTO commentDTO : commentDTOs) {
            Comment comment = commentDTO.toEntity();                      //CommentDTO -> comment Entity
            comment.addRecord(record);
            commentRepository.save(comment);
        }

        //keyword 저장,
        for(int i=0; i<keywordDTO.getComments().length; i++){
            Keyword keyword = keywordDTO.toEntity(i);           //keyDTO -> keyword Entity
            keyword.addRecord(record);
            keywordRepository.save(keyword);

            //keywordComment 저장.
            for(int j=0; j< keywordDTO.getComments()[i].length; j++) {
                KeywordComment keywordComment = keywordCommentDTO.toEntity(keywordDTO, i, j);  //keyDTO -> keywordComment Entity
                keywordComment.addKeyword(keyword);
                keywordCommentRepository.save(keywordComment);
            }
        }

        //percent 저장
        Percent percent = percentDTO.toEntity();   //percentDTO -> percent Entity
        percent.setRecord(record);
        percentRepository.save(percent);

        //videoInformation 저장.
        VideoInformation videoInformation = videoInformationDTO.toEntity();            //videoinformationDTO -> videoInformation Entity
        videoInformation.addRecord(record);
        videoInfoRepository.save(videoInformation);

        //interest 저장
        InterestDTO[] interests = interestListDTO.getInterests();
        for (InterestDTO interestDto : interests) {
            Interest interest = interestDto.toEntity();     //InterestDTO -> interest Entity
            interest.addRecord(record);
            interestRepository.save(interest);
        }

        //타임라인 저장.
        TimelineDTO[] timelines = timelineListDTO.getTimeline();
        for (TimelineDTO timelineDTO : timelines) {
            Timeline timeline = timelineDTO.toEntity();                   //TimelineDTO -> timeline Entity
            timeline.addRecord(record);
            timeLineRepository.save(timeline);
        }
    }


//    //분석 리스트 출력.
//    public List<RecordDTO> findRecords(Long id){
//        List<RecordDTO> recordDTOList = new ArrayList<>();
//        List<Record> records = recordRepository.findRecords(id);
//        for (Record recordEntity : records) {
//            RecordDTO recordDTO = new RecordDTO(recordEntity);              //Entity -> DTO
//            recordDTOList.add(recordDTO);
//        }
//        return recordDTOList;
//    }

    //Querydsl 리스트 출력
    public Page<RecordDTO> findRecordsPage(Long id, Pageable pageable){
        Page<RecordDTO> recordDTOS = recordRepository.searchPage(id, pageable);
        return recordDTOS;
    }

    //분석 1건 comment 조회
    public List<CommentDTO.Response> findComment(Long recordId){
        List<CommentDTO.Response> commentDTOList = new ArrayList<>();
        List<Comment> comments = commentRepository.findComments(recordId);
        for (Comment commentEntity : comments) {
            CommentDTO.Response response = new CommentDTO.Response(commentEntity);   //   Entity -> DTO
            commentDTOList.add(response);
        }
        return commentDTOList;
    }

    //분석 1건 interest 조회
    public List<InterestDTO.Response> findInterest(Long recordId){
        List<InterestDTO.Response> interestDTOList = new ArrayList<>();
        List<Interest> interests = interestRepository.findInterests(recordId);

        for (Interest interestEntity : interests) {
            InterestDTO.Response response = new InterestDTO.Response(interestEntity);   //   Entity -> DTO
            interestDTOList.add(response);
        }
        return interestDTOList;
    }

    //분석 1건 키워드 조회
    public List<KeywordDTO.Response> findKeyword(Long recordId){
        List<KeywordDTO.Response> keywordDTOList = new ArrayList<>();
        List<Keyword> keywords = keywordRepository.findKeywords(recordId);
        for (Keyword keywordEntity : keywords) {
            KeywordDTO.Response response = new KeywordDTO.Response(keywordEntity);   //   Entity -> DTO
            keywordDTOList.add(response);
        }
        return keywordDTOList;
    }

    //분석 1건 percent 조회.
    public PercentDTO.Response findPercent(Long recordId){
        Percent percentEntity = percentRepository.findPercents(recordId);
        PercentDTO.Response response = new PercentDTO.Response(percentEntity);      //   Entity -> DTO
        return response;
    }

    //분석 1건 타임라인 조회.
    public List<TimelineDTO.Response> findTimeLine(Long recordId){
        List<TimelineDTO.Response> timeLineDTOList = new ArrayList<>();
        List<Timeline> timelines = timeLineRepository.findTimelines(recordId);
        for (Timeline timelineEntity : timelines) {
            TimelineDTO.Response response = new TimelineDTO.Response(timelineEntity);     //   Entity -> DTO
            timeLineDTOList.add(response);
        }
        return timeLineDTOList;
    }

    //분석 1건 videoInfo 조회.
    public VideoInformationDTO.Response findVideoInfo(Long recordId){
        VideoInformation videoInfo = videoInfoRepository.findVideoInfo(recordId);
        VideoInformationDTO.Response response = new VideoInformationDTO.Response(videoInfo);     //Entity -> DTO
        return response;
    }

    //분석 1건 키워드 관련 댓글 조회.
    public List<KeywordCommentDTO.Response> findKeywordComment(Long recordId){
        List<KeywordCommentDTO.Response> keywordCommentDTOList = new ArrayList<>();

        List<KeywordComment> keywordComments = keywordCommentRepository.findKeywordComments(recordId);
        for (KeywordComment keywordCommentEntity : keywordComments) {
            KeywordCommentDTO.Response response = new KeywordCommentDTO.Response(keywordCommentEntity);  //   Entity -> DTO
            keywordCommentDTOList.add(response);
        }
        return keywordCommentDTOList;
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
