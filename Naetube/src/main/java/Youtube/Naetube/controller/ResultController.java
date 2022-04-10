package Youtube.Naetube.controller;

import Youtube.Naetube.domain.*;
import Youtube.Naetube.service.CommentService;
import Youtube.Naetube.service.InterestService;
import Youtube.Naetube.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ResultController {

    //서비스 클래스 DI
    private final InterestService interestService;
    private final VideoService videoService;
    private final CommentService commentService;

    //결과 화면
    @GetMapping("/search/{url}")
    public String Search(@PathVariable String url, Model model){

        /**베스트 키워드 start*/
        String KeywordBaseUrl = "http://localhost:5000/searchKeyword?url=" + url;
        RestTemplate KeywordRestTemplate = new RestTemplate();

        ResponseEntity<Keyword> KeywordResponse = KeywordRestTemplate.getForEntity(KeywordBaseUrl, Keyword.class);

        Keyword keyword = KeywordResponse.getBody();
        log.info("keyeqwerqeeword={}", keyword);
        log.info("keyword.getB5()[0]={}", keyword.getB5()[0]);
        log.info("keyword.getComments()={}", keyword.getComments());
        log.info("keyword.getComments()[0][0]={}", keyword.getComments()[0][0]);
        model.addAttribute("keyword",keyword);
        /** 베스트 키워드 end */

        /**타임라인 start*/
        String TimelineBaseUrl = "http://localhost:5000/timeline?url="+url;
        RestTemplate TimelineRestTemplate = new RestTemplate();
        ResponseEntity<Timeline[]> TimelineResponse = TimelineRestTemplate.getForEntity(TimelineBaseUrl, Timeline[].class);
        Timeline[] timelines = TimelineResponse.getBody();
        model.addAttribute("timelines",timelines);
        /** 타임라인 end */


        /**관심도 start*/
        String InterestBaseUrl = "http://localhost:5000/interest?url=" + url;
        RestTemplate InterestRestTemplate = new RestTemplate();
        ResponseEntity<Interest[]> InterestResponse = InterestRestTemplate.getForEntity(InterestBaseUrl, Interest[].class);
        Interest interests[] = InterestResponse.getBody();

        model.addAttribute("size",interests.length);
        model.addAttribute("commentDate",interestService.countDate(interests));
        model.addAttribute("commentCount",interestService.countComment(interests));
        /**관심도 end*/

        /**비디오 정보 가져오기 start */
        String VIbaseUrl = "http://localhost:5000/getVideoInformation?url=" + url;
        RestTemplate VIrestTemplate = new RestTemplate();
        ResponseEntity<VideoInformation[]> VIresponse = VIrestTemplate.getForEntity(VIbaseUrl, VideoInformation[].class);
        VideoInformation[] videoInformation = VIresponse.getBody();

        model.addAttribute("videoTitle",videoInformation[0].getTitle());
        model.addAttribute("videoDate",videoService.dateData(videoInformation));
        model.addAttribute("videoView",videoService.viewData(videoInformation));
        /**비디오 정보 가져오기 end */

        /**긍정부정, 6가지 감정 start*/
        String baseUrl = "http://localhost:5000/classifyComments?url=" + url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(baseUrl, Comment[].class);
        Comment comments[] = response.getBody();

        HashMap<String, List> commentMap = commentService.classifyComment(comments);
        HashMap<String, Double> positiveNegativePercentMap = commentService.positiveNegativePercent();
        HashMap<String, Double> sentimentPercentMap = commentService.sentimentPercent();

        System.out.println(commentMap);
        System.out.println("====================================");

        JSONObject json =  new JSONObject(commentMap);


        System.out.println(json);


        model.addAttribute("url", "https://www.youtube.com/embed/"+url);    //search.html에 url 전달.
        model.addAttribute("positiveComments",commentMap.get("positiveComments"));
        model.addAttribute("negativeComments",commentMap.get("negativeComments"));
        model.addAttribute("comments",comments);
        model.addAttribute("positivePercent", positiveNegativePercentMap.get("positivePercent"));
        model.addAttribute("negativePercent", positiveNegativePercentMap.get("negativePercent"));
        model.addAttribute("fearPercent",sentimentPercentMap.get("refined_fearPercent"));
        model.addAttribute("surprisedPercent",sentimentPercentMap.get("refined_surprisedPercent"));
        model.addAttribute("angerPercent",sentimentPercentMap.get("refined_angerPercent"));
        model.addAttribute("sadnessPercent",sentimentPercentMap.get("refined_sadnessPercent"));
        model.addAttribute("neutralPercent",sentimentPercentMap.get("refined_neutralPercent"));
        model.addAttribute("happyPercent",sentimentPercentMap.get("refined_happyPercent"));
        model.addAttribute("disgustPercent",sentimentPercentMap.get("refined_disgustPercent"));

        model.addAttribute("fearComments",commentMap.get("fearComments"));
        model.addAttribute("surprisedComments",commentMap.get("surprisedComments"));
        model.addAttribute("angerComments",commentMap.get("angerComments"));
        model.addAttribute("sadnessComments",commentMap.get("sadnessComments"));
        model.addAttribute("neutralComments",commentMap.get("neutralComments"));
        model.addAttribute("happyComments",commentMap.get("happyComments"));
        model.addAttribute("disgustComments",commentMap.get("disgustComments"));

        /**긍정부정, 6가지 감정 end*/

        model.addAttribute("videoId",url);

        return "search";
    }


//    @GetMapping("/findcomment")
//    @ResponseBody
//    public String[] findComment(@RequestParam("url") String url, @RequestParam("keyword") String keyword){
//        String baseUrl = "http://localhost:5000/find?url=" + url+"&keyword="+keyword;
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String[]> response = restTemplate.getForEntity(baseUrl, String[].class);
//        String[] result = response.getBody();
//        return result;
//    }

    @GetMapping("/find/{url}")
    public String find(@PathVariable String url, Model model){
        model.addAttribute("url",url);
        return "find";
    }

    @GetMapping("/loading")
    public String loading(){

        return "loading";
    }
}


