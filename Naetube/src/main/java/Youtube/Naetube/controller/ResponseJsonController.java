package Youtube.Naetube.controller;

import Youtube.Naetube.domain.Comment;
import Youtube.Naetube.domain.Keyword;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@Slf4j
@RestController
public class ResponseJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();


    @CrossOrigin("*")
    @GetMapping("/test")
    public ResponseEntity<String> test(@PathVariable String url, Model model) {
        String data = "abcddddd";

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }


    @CrossOrigin("*")
    @GetMapping("/getKeyword/{url}")
    public Keyword getKeyword(@PathVariable String url, Model model) {
        String KeywordBaseUrl = "http://localhost:5000/searchKeyword?url=" + url;
        RestTemplate KeywordRestTemplate = new RestTemplate();

        ResponseEntity<Keyword> KeywordResponse = KeywordRestTemplate.getForEntity(KeywordBaseUrl, Keyword.class);

        Keyword keyword = KeywordResponse.getBody();


        return keyword;
    }

    @CrossOrigin("*")
    @GetMapping("/getComments/{url}")
    public String getComments(@PathVariable String url, Model model) {

        String baseUrl = "http://localhost:5000/classifyComments?url=" + url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(baseUrl, Comment[].class);
        Comment comments[] = response.getBody();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String jsonCommentsData = gson.toJson(comments);
        System.out.println(jsonCommentsData);


        return jsonCommentsData;
    }
}
