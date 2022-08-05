package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.dto.*;
import Youtube.SpringbootServer.service.CommentService;
import Youtube.SpringbootServer.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ResponseJsonController {

    // DI
    private final VideoService videoService;
    private final CommentService commentService;
    private final KeywordDTO keywordDTO;
    private final CommentListDTO commentListDTO;
    private final PercentDTO percentDTO;
    private final VideoInformationDTO videoInformationDTO;
    private final InterestListDTO interestListDTO;
    private final TimeLineListDTO timeLineListDTO;

    @CrossOrigin("*")
    @GetMapping("/keywords/{url}")
    public KeywordDTO getKeyword(@PathVariable String url, Model model) {
        String KeywordBaseUrl = "http://localhost:5000/keywords?url=" + url;
        RestTemplate KeywordRestTemplate = new RestTemplate();

        ResponseEntity<KeywordDTO> KeywordResponse = KeywordRestTemplate.getForEntity(KeywordBaseUrl, KeywordDTO.class);

        KeywordDTO keyword = KeywordResponse.getBody();
        keywordDTO.setB5(keyword.getB5());
        keywordDTO.setComments(keyword.getComments());
        log.info("대표 키워드 1 = {}", keyword.getB5()[0]);
        log.info("대표 키워드 2 = {}", keyword.getB5()[1]);
        log.info("대표 키워드 3 = {}", keyword.getB5()[2]);
        log.info("대표 키워드 4 = {}", keyword.getB5()[3]);
        log.info("대표 키워드 5 = {}", keyword.getB5()[4]);
        log.info("키워드 1 대표 댓글 = {}", keyword.getComments()[0][0]);
        log.info("키워드 2 대표 댓글 = {}", keyword.getComments()[1][0]);
        log.info("키워드 3 대표 댓글 = {}", keyword.getComments()[2][0]);
        log.info("키워드 4 대표 댓글 = {}", keyword.getComments()[3][0]);
        log.info("키워드 5 대표 댓글 = {}", keyword.getComments()[4][0]);


        return keyword;
    }

    @CrossOrigin("*")
    @GetMapping("/comments/{url}")
    public CommentListDTO getComments(@PathVariable String url, Model model) {

        String baseUrl = "http://localhost:5000/comments?url=" + url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CommentDTO[]> response = restTemplate.getForEntity(baseUrl, CommentDTO[].class);
        CommentDTO[] comments = response.getBody();

        HashMap<String, List> commentMap = commentService.classifyComment(comments);
        HashMap<String, Double> positiveNegativePercentMap = commentService.positiveNegativePercent();
        HashMap<String, Double> sentimentPercentMap = commentService.sentimentPercent();

        percentDTO.SetPercentDTO(positiveNegativePercentMap.get("refined_positivePercent"),positiveNegativePercentMap.get("refined_negativePercent"),
                sentimentPercentMap.get("refined_happyPercent"),sentimentPercentMap.get("refined_surprisedPercent"),sentimentPercentMap.get("refined_angerPercent"),
                sentimentPercentMap.get("refined_sadnessPercent"),sentimentPercentMap.get("refined_neutralPercent"),sentimentPercentMap.get("refined_disgustPercent"),
                sentimentPercentMap.get("refined_fearPercent"));
        commentListDTO.setCommentListDTO(comments,percentDTO);

        return commentListDTO;
    }

    @CrossOrigin("*")
    @GetMapping("/videoinformations/{videoId}")
    public VideoInformationDTO getVideoInfo(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/videoinformations?url=" + videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VideoInformationDTO[]> response = restTemplate.getForEntity(baseurl, VideoInformationDTO[].class);
        VideoInformationDTO[] videoInfo = response.getBody();
        for (VideoInformationDTO vi : videoInfo) {
            videoInformationDTO.setVideoInfo(vi.getTitle(),vi.getDate(),vi.getView(),vi.getLike());
        }
        System.out.println(videoInfo);
        return videoInformationDTO;
    }

    @CrossOrigin("*")
    @GetMapping("/timelines/{videoId}")
    public TimelineDTO[] getTimeline(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/timelines?url="+videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TimelineDTO[]> response = restTemplate.getForEntity(baseurl, TimelineDTO[].class);
        TimelineDTO[] timeline = response.getBody();
        timeLineListDTO.setTimeline(timeline);
        System.out.println("timeline = " + timeline);
        return timeline;
    }

    @CrossOrigin("*")
    @GetMapping("/interest/{videoId}")
    public InterestDTO[] getInterest(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/interest?url=" + videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<InterestDTO[]> InterestResponse = restTemplate.getForEntity(baseurl, InterestDTO[].class);
        InterestDTO[] interests = InterestResponse.getBody();
        interestListDTO.setInterests(interests);

        for(int i=0;i<interests.length;i++){
            log.info("날짜별 댓글 개수 = {}", interests[i]);
        }
        System.out.println("interests = " + interests);
        return interests;
    }

    @CrossOrigin("*")
    @GetMapping("/search")
    public String[] findComment(@RequestParam("url") String url, @RequestParam("keyword") String keyword){
        String baseUrl = "http://localhost:5000/search?url=" + url+"&keyword="+keyword;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> response = restTemplate.getForEntity(baseUrl, String[].class);
        String[] result = response.getBody();
        return result;
    }
}
