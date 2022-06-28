package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.domain.*;
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

    //서비스 클래스 DI
    private final VideoService videoService;
    private final CommentService commentService;

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
    public Keyword getKeyword(@PathVariable String url, Model model) {
        String KeywordBaseUrl = "http://localhost:5000/searchkeyword?url=" + url;
        RestTemplate KeywordRestTemplate = new RestTemplate();

        ResponseEntity<Keyword> KeywordResponse = KeywordRestTemplate.getForEntity(KeywordBaseUrl, Keyword.class);

        Keyword keyword = KeywordResponse.getBody();

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
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(baseUrl, Comment[].class);
        Comment comments[] = response.getBody();

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


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonDataArrayToGson = gson.toJson(jsonDataArray);




        return jsonDataArrayToGson;
    }

    @CrossOrigin("*")
    @GetMapping("/videoinfo/{videoId}")
    public String getVideoInfo(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/getvideoinformation?url=" + videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VideoInformation[]> response = restTemplate.getForEntity(baseurl, VideoInformation[].class);
        VideoInformation[] videoInfo = response.getBody();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(videoInfo);
        return json;
    }

    @CrossOrigin("*")
    @GetMapping("/timeline/{videoId}")
    public String getTimeline(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/timeline?url="+videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Timeline[]> response = restTemplate.getForEntity(baseurl, Timeline[].class);
        Timeline[] timeline = response.getBody();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(timeline);
        return json;
    }

    @CrossOrigin("*")
    @GetMapping("/interest/{videoId}")
    public String getInterest(@PathVariable String videoId){
        String baseurl = "http://localhost:5000/interest?url=" + videoId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Interest[]> InterestResponse = restTemplate.getForEntity(baseurl, Interest[].class);
        Interest[] interests = InterestResponse.getBody();

        for(int i=0;i<interests.length;i++){
            log.info("날짜별 댓글 개수 = {}", interests[i]);
        }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(interests);
        return json;
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
