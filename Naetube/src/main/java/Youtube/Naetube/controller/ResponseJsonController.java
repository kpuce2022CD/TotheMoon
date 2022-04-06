package Youtube.Naetube.controller;

import Youtube.Naetube.domain.Comment;
import Youtube.Naetube.domain.Keyword;
import Youtube.Naetube.service.CommentService;
import Youtube.Naetube.service.InterestService;
import Youtube.Naetube.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;



@Slf4j
@RestController
@RequiredArgsConstructor
public class ResponseJsonController {

    //서비스 클래스 DI
    private final InterestService interestService;
    private final VideoService videoService;
    private final CommentService commentService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @CrossOrigin("*")
    @GetMapping("/test")
    public ResponseEntity<String> test(@PathVariable String url, Model model) {
        String data = "abcddddd";

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }


    @CrossOrigin("*")
    @GetMapping("/getkeyword/{url}")
    public Keyword getKeyword(@PathVariable String url, Model model) {
        String KeywordBaseUrl = "http://localhost:5000/searchkeyword?url=" + url;
        RestTemplate KeywordRestTemplate = new RestTemplate();

        ResponseEntity<Keyword> KeywordResponse = KeywordRestTemplate.getForEntity(KeywordBaseUrl, Keyword.class);

        Keyword keyword = KeywordResponse.getBody();


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
}
