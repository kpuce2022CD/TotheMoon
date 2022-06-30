package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.dto.*;
import Youtube.SpringbootServer.entity.Keyword;
import Youtube.SpringbootServer.service.BoardService;
import Youtube.SpringbootServer.service.CommentService;
import Youtube.SpringbootServer.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;

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

    private ObjectMapper objectMapper = new ObjectMapper();
    private String link = "http://localhost:5000";
    private RestTemplate restTemplate = new RestTemplate();

    @CrossOrigin("*")
    @GetMapping("/youtube-video-id/{videoId}")
    public String setVideoId(@PathVariable String videoId, HttpServletRequest request){
        String baseUrl = link + "/";
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl, String.class);
        String comments = response.getBody();
        HttpSession httpSession = request.getSession();
        //String to comments domain
        //        httpSession
        return null;
    }

    @CrossOrigin("*")
    @GetMapping("/getkeyword/{url}")
    public KeywordDTO getKeyword(@PathVariable String url, Model model) {
        String KeywordBaseUrl = "http://localhost:5000/searchkeyword?url=" + url;
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
    @GetMapping("/getcomments/{url}")
    public String getComments(@PathVariable String url, Model model) {

        String baseUrl = "http://localhost:5000/classifycomments?url=" + url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CommentDTO[]> response = restTemplate.getForEntity(baseUrl, CommentDTO[].class);
        CommentDTO comments[] = response.getBody();
        commentListDTO.setComments(comments);
        HashMap<String, List> commentMap = commentService.classifyComment(comments);
        HashMap<String, Double> positiveNegativePercentMap = commentService.positiveNegativePercent();
        HashMap<String, Double> sentimentPercentMap = commentService.sentimentPercent();

        JSONObject positivePercent = new JSONObject();
        JSONObject negativePercent = new JSONObject();
        JSONObject happyPercent = new JSONObject();
        JSONObject surprisedPercent = new JSONObject();
        JSONObject angerPercent = new JSONObject();
        JSONObject sadnessPercent = new JSONObject();
        JSONObject neutralPercent = new JSONObject();
        JSONObject disgustPercent = new JSONObject();
        JSONObject fearPercent = new JSONObject();

        JSONArray jsonDataArray = new JSONArray();

        for (int i = 0; i < comments.length; i++) {
            jsonDataArray.add(comments[i]);
        }


        positivePercent.put("index", "9");
        positivePercent.put("positivePercent", positiveNegativePercentMap.get("refined_positivePercent"));
        jsonDataArray.add(positivePercent);

        negativePercent.put("index", "10");
        negativePercent.put("negativePercent", positiveNegativePercentMap.get("refined_negativePercent"));
        jsonDataArray.add(negativePercent);

        happyPercent.put("index", "11");
        happyPercent.put("happyPercent", sentimentPercentMap.get("refined_happyPercent"));
        jsonDataArray.add(happyPercent);

        surprisedPercent.put("index", "12");
        surprisedPercent.put("surprisedPercent", sentimentPercentMap.get("refined_surprisedPercent"));
        jsonDataArray.add(surprisedPercent);

        angerPercent.put("index", "13");
        angerPercent.put("angerPercent", sentimentPercentMap.get("refined_angerPercent"));
        jsonDataArray.add(angerPercent);

        sadnessPercent.put("index", "14");
        sadnessPercent.put("sadnessPercent", sentimentPercentMap.get("refined_sadnessPercent"));
        jsonDataArray.add(sadnessPercent);

        neutralPercent.put("index", "15");
        neutralPercent.put("neutralPercent", sentimentPercentMap.get("refined_neutralPercent"));
        jsonDataArray.add(neutralPercent);

        disgustPercent.put("index", "16");
        disgustPercent.put("disgustPercent", sentimentPercentMap.get("refined_disgustPercent"));
        jsonDataArray.add(disgustPercent);

        fearPercent.put("index", "17");
        fearPercent.put("fearPercent", sentimentPercentMap.get("refined_fearPercent"));
        jsonDataArray.add(fearPercent);

        percentDTO.SetPercentDTO(positiveNegativePercentMap.get("refined_positivePercent"),positiveNegativePercentMap.get("refined_negativePercent"),
                sentimentPercentMap.get("refined_happyPercent"),sentimentPercentMap.get("refined_surprisedPercent"),sentimentPercentMap.get("refined_angerPercent"),
                sentimentPercentMap.get("refined_sadnessPercent"),sentimentPercentMap.get("refined_neutralPercent"),sentimentPercentMap.get("refined_disgustPercent"),
                sentimentPercentMap.get("refined_fearPercent"));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonDataArrayToGson = gson.toJson(jsonDataArray);

        return jsonDataArrayToGson;
    }

    @CrossOrigin("*")
    @GetMapping("/videoinfo/{videoId}")
    public VideoInformationDTO[] getVideoInfo(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/getvideoinformation?url=" + videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VideoInformationDTO[]> response = restTemplate.getForEntity(baseurl, VideoInformationDTO[].class);
        VideoInformationDTO[] videoInfo = response.getBody();
        System.out.println(videoInfo);
        return videoInfo;
    }

    @CrossOrigin("*")
    @GetMapping("/timeline/{videoId}")
    public TimelineDTO[] getTimeline(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/timeline?url="+videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TimelineDTO[]> response = restTemplate.getForEntity(baseurl, TimelineDTO[].class);
        TimelineDTO[] timeline = response.getBody();
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

        for(int i=0;i<interests.length;i++){
            log.info("날짜별 댓글 개수 = {}", interests[i]);
        }
        System.out.println("interests = " + interests);
        return interests;
    }

    @CrossOrigin("*")
    @GetMapping("/findcomment")
    public String[] findComment(@RequestParam("url") String url, @RequestParam("keyword") String keyword){
        String baseUrl = "http://localhost:5000/find?url=" + url+"&keyword="+keyword;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> response = restTemplate.getForEntity(baseUrl, String[].class);
        String[] result = response.getBody();
        return result;
    }
}
