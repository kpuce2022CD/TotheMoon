package Youtube.Naetube.controller;

import Youtube.Naetube.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    private ObjectMapper objectMapper = new ObjectMapper();


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

        String[] commentDate = new String[interests.length];
        String[] commentCount = new String[interests.length];

        for(int i=0;i< interests.length;i++){
            commentDate[i]=interests[i].getCommentDate();
            commentCount[i]=interests[i].getCommentCount();
        }

        model.addAttribute("size",interests.length);
        model.addAttribute("commentDate",commentDate);
        model.addAttribute("commentCount",commentCount);
        /**관심도 end*/

        /**비디오 정보 가져오기 start */
        String VIbaseUrl = "http://localhost:5000/getVideoInformation?url=" + url;
        RestTemplate VIrestTemplate = new RestTemplate();
        ResponseEntity<VideoInformation[]> VIresponse = VIrestTemplate.getForEntity(VIbaseUrl, VideoInformation[].class);
        VideoInformation[] videoInformation = VIresponse.getBody();
        DecimalFormat decFormat = new DecimalFormat("###,###");


        int view = Integer.parseInt(videoInformation[0].getView());
        String viewData = decFormat.format(view);

        String DateDate = videoInformation[0].getDate().replace('-','.');

        model.addAttribute("videoTitle",videoInformation[0].getTitle());
        model.addAttribute("videoDate",DateDate);
        model.addAttribute("videoView",viewData);

        /**비디오 정보 가져오기 end */

        /**긍정부정, 6가지 감정 start*/
        String baseUrl = "http://localhost:5000/classifyComments?url=" + url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Comment[]> response = restTemplate.getForEntity(baseUrl, Comment[].class);

        Comment comments[] = response.getBody();

        List<Comment> positiveComments = new ArrayList<>(); // json 구분 인덱스 : 1
        List<Comment> negativeComments = new ArrayList<>(); // json 구분 인덱스 : 0
        List<Comment> fearComments = new ArrayList<>(); // json 구분 인덱스 : 2
        List<Comment> surprisedComments = new ArrayList<>(); // json 구분 인덱스 : 3
        List<Comment> angerComments = new ArrayList<>(); // json 구분 인덱스 : 4
        List<Comment> sadnessComments = new ArrayList<>(); // json 구분 인덱스 : 5
        List<Comment> neutralComments = new ArrayList<>(); // json 구분 인덱스 : 6
        List<Comment> happyComments = new ArrayList<>(); // json 구분 인덱스 : 7
        List<Comment> disgustComments = new ArrayList<>(); // json 구분 인덱스 : 8

        //긍정 부정, 6가지 감정 댓글 분류
        classifyComment(comments, positiveComments, negativeComments, fearComments, surprisedComments, angerComments, sadnessComments, neutralComments, happyComments, disgustComments);


        System.out.println("positiveComments = " + positiveComments);
        //[Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0) , ...]
        System.out.println("positiveComments[0] = " + positiveComments.get(0));
        //Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0)

        double positivePercent = ((double)positiveComments.size() / ((double)positiveComments.size()+(double)negativeComments.size()))*100;
        double negativePercent = ((double)negativeComments.size() / ((double)positiveComments.size()+(double)negativeComments.size()))*100;
        double emTotalSize = fearComments.size()+ surprisedComments.size()+ angerComments.size()+ sadnessComments.size()+ neutralComments.size()+ happyComments.size()+ disgustComments.size();
        double fearPercent = (((double)fearComments.size()/emTotalSize)*100);
        double surprisedPercent = ((double)surprisedComments.size()/emTotalSize)*100;
        double angerPercent = ((double)angerComments.size()/emTotalSize)*100;
        double sadnessPercent = ((double)sadnessComments.size()/emTotalSize)*100;
        double neutralPercent = ((double)neutralComments.size()/emTotalSize)*100;
        double happyPercent = ((double)happyComments.size()/emTotalSize)*100;
        double disgustPercent = ((double)disgustComments.size()/emTotalSize)*100;

        // 소수점 정규화
        double return_fearPercent = Double.parseDouble(String.format("%.1f",fearPercent));
        double return_surprisedPercent = Double.parseDouble(String.format("%.1f",surprisedPercent));
        double return_angerPercent = Double.parseDouble(String.format("%.1f",angerPercent));
        double return_sadnessPercent = Double.parseDouble(String.format("%.1f",sadnessPercent));
        double return_neutralPercent = Double.parseDouble(String.format("%.1f",neutralPercent));
        double return_happyPercent = Double.parseDouble(String.format("%.1f",happyPercent));
        double return_disgustPercent = Double.parseDouble(String.format("%.1f",disgustPercent));



        System.out.println("전체 댓글 수 = " + comments.length);
        System.out.println("긍정 댓글 수 = " + positiveComments.size());
        System.out.println("부정 댓글 수  = " + negativeComments.size());
        System.out.println("긍정 댓글 비율 = " + positivePercent);
        System.out.println("부정 댓글 비율 = " + negativePercent);
        System.out.println("공포 댓글 비율 = " + return_fearPercent);
        System.out.println("놀람 댓글 비율 = " + return_surprisedPercent);
        System.out.println("분노 댓글 비율 = " + return_angerPercent);
        System.out.println("슬픔 댓글 비율 = " + return_sadnessPercent);
        System.out.println("중립 댓글 비율 = " + return_neutralPercent);
        System.out.println("행복 댓글 비율 = " + return_happyPercent);
        System.out.println("혐오 댓글 비율 = " + return_disgustPercent);


        // "행복" 댓글 출력 테스트
        //for(int i=0;i<happyComments.size();i++){
        //    System.out.println(happyComments.get(i).getComment());
        //}

        model.addAttribute("url", "https://www.youtube.com/embed/"+url);    //search.html에 url 전달.
        model.addAttribute("positiveComments",positiveComments);
        model.addAttribute("negativeComments",negativeComments);
        model.addAttribute("comments",comments);
        model.addAttribute("positivePercent", positivePercent);
        model.addAttribute("negativePercent", negativePercent);
        model.addAttribute("fearPercent",return_fearPercent);
        model.addAttribute("surprisedPercent",return_surprisedPercent);
        model.addAttribute("angerPercent",return_angerPercent);
        model.addAttribute("sadnessPercent",return_sadnessPercent);
        model.addAttribute("neutralPercent",return_neutralPercent);
        model.addAttribute("happyPercent",return_happyPercent);
        model.addAttribute("disgustPercent",return_disgustPercent);
        model.addAttribute("fearComments",fearComments);
        model.addAttribute("surprisedComments",surprisedComments);
        model.addAttribute("angerComments",angerComments);
        model.addAttribute("sadnessComments",sadnessComments);
        model.addAttribute("neutralComments",neutralComments);
        model.addAttribute("happyComments",happyComments);
        model.addAttribute("disgustComments",disgustComments);

        /**긍정부정, 6가지 감정 end*/

        model.addAttribute("videoId",url);

        return "search";
    }

//    @GetMapping("/keyword/{url}")
//    public String getKeyword(@PathVariable String url, Model model){
//        String baseUrl = "http://localhost:5000/searchKeyword?url=" + url;
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<Keyword> response = restTemplate.getForEntity(baseUrl, Keyword.class);
//
//        Keyword keyword = response.getBody();
//        log.info("keyeqwerqeeword={}", keyword);
//        log.info("keyword.getB5()[0]={}", keyword.getB5()[0]);
//        log.info("keyword.getComments()={}", keyword.getComments());
//        log.info("keyword.getComments()[0][0]={}", keyword.getComments()[0][0]);
//        model.addAttribute("keyword",keyword);
//        return "keyword";
//    }

    @GetMapping("/timeline/{url}")
    public String getTimeline(@PathVariable String url, Model model){

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

        String baseUrl = "http://localhost:5000/timeline?url="+url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Timeline[]> response = restTemplate.getForEntity(baseUrl, Timeline[].class);
        Timeline[] timelines = response.getBody();
        model.addAttribute("timelines",timelines);
        model.addAttribute("url",url);
        return "timeline";
    }

    @GetMapping("/test/{url}")
    public String test_search(@PathVariable String url, Model model){

        Comment[] comments = new Comment[900];
        Interest[] interests = new Interest[10];
        String[] commentDate = new String[10];
        String[] commentCount = new String[10];

        for(int i=0;i<100;i++){
            comments[i] = new Comment("0","testId","부정테스트입니다.부정테스트입니다.부정테스트입니다.부정테스트입니다.부정테스트입니다.부정테스트입니다","2020-20-20","5");
        }
        for(int i=100;i<200;i++){
            comments[i] = new Comment("1","testId","긍정테스트입니다.긍정테스트입니다.긍정테스트입니다.긍정테스트입니다.긍정테스트입니다.긍정테스트입니다.","2020-20-20","5");

        }
        for(int i=200;i<300;i++){
            comments[i] = new Comment("2","testId","공포테스트입니다.공포테스트입니다.공포테스트입니다.공포테스트입니다.공포테스트입니다.공포테스트입니다.","2020-20-20","5");

        }
        for(int i=300;i<400;i++){
            comments[i] = new Comment("3","testId","놀람테스트입니다.놀람테스트입니다.놀람테스트입니다.놀람테스트입니다.놀람테스트입니다.놀람테스트입니다.","2020-20-20","5");

        }
        for(int i=400;i<500;i++){
            comments[i] = new Comment("4","testId","분노테스트입니다.분노테스트입니다.분노테스트입니다.분노테스트입니다.분노테스트입니다.분노테스트입니다.","2020-20-20","5");

        }
        for(int i=500;i<600;i++){
            comments[i] = new Comment("5","testId","슬픔테스트입니다.슬픔테스트입니다.슬픔테스트입니다.슬픔테스트입니다.슬픔테스트입니다.슬픔테스트입니다.","2020-20-20","5");

        }
        for(int i=600;i<700;i++){
            comments[i] = new Comment("6","testId","중립테스트입니다.중립테스트입니다.중립테스트입니다.중립테스트입니다.중립테스트입니다.중립테스트입니다.","2020-20-20","5");

        }
        for(int i=700;i<800;i++){
            comments[i] = new Comment("7","testId","행복테스트입니다.행복테스트입니다.행복테스트입니다.행복테스트입니다.행복테스트입니다.행복테스트입니다.","2020-20-20","5");

        }
        for(int i=800;i<900;i++){
            comments[i] = new Comment("8","testId","혐오테스트입니다.혐오테스트입니다.혐오테스트입니다.혐오테스트입니다.혐오테스트입니다.혐오테스트입니다.","2020-20-20","5");

        }



        interests[0] = new Interest("2021-11-11","0");
        interests[1] = new Interest("2021-11-12","22");
        interests[2] = new Interest("2021-11-13","32");
        interests[3] = new Interest("2021-11-14","25");
        interests[4] = new Interest("2021-11-15","15");
        interests[5] = new Interest("2021-11-16","7");
        interests[6] = new Interest("2021-11-17","15");
        interests[7] = new Interest("2021-11-18","19");
        interests[8] = new Interest("2021-11-19","30");
        interests[9] = new Interest("2021-11-20","45");


        for(int i=0;i<interests.length;i++){
            commentDate[i]=interests[i].getCommentDate();
            commentCount[i]=interests[i].getCommentCount();
        }

        List<Comment> positiveComments = new ArrayList<>(); // json 구분 인덱스 : 1
        List<Comment> negativeComments = new ArrayList<>(); // json 구분 인덱스 : 0
        List<Comment> fearComments = new ArrayList<>(); // json 구분 인덱스 : 2
        List<Comment> surprisedComments = new ArrayList<>(); // json 구분 인덱스 : 3
        List<Comment> angerComments = new ArrayList<>(); // json 구분 인덱스 : 4
        List<Comment> sadnessComments = new ArrayList<>(); // json 구분 인덱스 : 5
        List<Comment> neutralComments = new ArrayList<>(); // json 구분 인덱스 : 6
        List<Comment> happyComments = new ArrayList<>(); // json 구분 인덱스 : 7
        List<Comment> disgustComments = new ArrayList<>(); // json 구분 인덱스 : 8

        //댓글 분류
        classifyComment(comments, positiveComments, negativeComments, fearComments, surprisedComments, angerComments, sadnessComments, neutralComments, happyComments, disgustComments);


        System.out.println("positiveComments = " + positiveComments);
        //[Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0) , ...]
        System.out.println("positiveComments[0] = " + positiveComments.get(0));
        //Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0)

        double positivePercent = ((double)positiveComments.size() / ((double)positiveComments.size()+(double)negativeComments.size()))*100;
        double negativePercent = ((double)negativeComments.size() / ((double)positiveComments.size()+(double)negativeComments.size()))*100;
        double emTotalSize = fearComments.size()+ surprisedComments.size()+ angerComments.size()+ sadnessComments.size()+ neutralComments.size()+ happyComments.size()+ disgustComments.size();
        double fearPercent = (((double)fearComments.size()/emTotalSize)*100);
        double surprisedPercent = ((double)surprisedComments.size()/emTotalSize)*100;
        double angerPercent = ((double)angerComments.size()/emTotalSize)*100;
        double sadnessPercent = ((double)sadnessComments.size()/emTotalSize)*100;
        double neutralPercent = ((double)neutralComments.size()/emTotalSize)*100;
        double happyPercent = ((double)happyComments.size()/emTotalSize)*100;
        double disgustPercent = ((double)disgustComments.size()/emTotalSize)*100;

        // 소수점 정규화
        double return_fearPercent = Double.parseDouble(String.format("%.1f",fearPercent));
        double return_surprisedPercent = Double.parseDouble(String.format("%.1f",surprisedPercent));
        double return_angerPercent = Double.parseDouble(String.format("%.1f",angerPercent));
        double return_sadnessPercent = Double.parseDouble(String.format("%.1f",sadnessPercent));
        double return_neutralPercent = Double.parseDouble(String.format("%.1f",neutralPercent));
        double return_happyPercent = Double.parseDouble(String.format("%.1f",happyPercent));
        double return_disgustPercent = Double.parseDouble(String.format("%.1f",disgustPercent));



        System.out.println("전체 댓글 수 = " + comments.length);
        System.out.println("긍정 댓글 수 = " + positiveComments.size());
        System.out.println("부정 댓글 수  = " + negativeComments.size());
        System.out.println("긍정 댓글 비율 = " + positivePercent);
        System.out.println("부정 댓글 비율 = " + negativePercent);
        System.out.println("공포 댓글 비율 = " + return_fearPercent);
        System.out.println("놀람 댓글 비율 = " + return_surprisedPercent);
        System.out.println("분노 댓글 비율 = " + return_angerPercent);
        System.out.println("슬픔 댓글 비율 = " + return_sadnessPercent);
        System.out.println("중립 댓글 비율 = " + return_neutralPercent);
        System.out.println("행복 댓글 비율 = " + return_happyPercent);
        System.out.println("혐오 댓글 비율 = " + return_disgustPercent);


        // "행복" 댓글 출력 테스트
        //for(int i=0;i<happyComments.size();i++){
        //    System.out.println(happyComments.get(i).getComment());
        //}

        model.addAttribute("url", "https://www.youtube.com/embed/"+url);    //search.html에 url 전달.
        model.addAttribute("positiveComments",positiveComments);
        model.addAttribute("negativeComments",negativeComments);
        model.addAttribute("comments",comments);
        model.addAttribute("positivePercent", positivePercent);
        model.addAttribute("negativePercent", negativePercent);
        model.addAttribute("fearPercent",return_fearPercent);
        model.addAttribute("surprisedPercent",return_surprisedPercent);
        model.addAttribute("angerPercent",return_angerPercent);
        model.addAttribute("sadnessPercent",return_sadnessPercent);
        model.addAttribute("neutralPercent",return_neutralPercent);
        model.addAttribute("happyPercent",return_happyPercent);
        model.addAttribute("disgustPercent",return_disgustPercent);
        model.addAttribute("fearComments",fearComments);
        model.addAttribute("surprisedComments",surprisedComments);
        model.addAttribute("angerComments",angerComments);
        model.addAttribute("sadnessComments",sadnessComments);
        model.addAttribute("neutralComments",neutralComments);
        model.addAttribute("happyComments",happyComments);
        model.addAttribute("disgustComments",disgustComments);

        model.addAttribute("size",interests.length);
        model.addAttribute("commentDate",commentDate);
        model.addAttribute("commentCount",commentCount);
        model.addAttribute("videoId",url);

        return "test_search";
    }

    @GetMapping("/findcomment")
    @ResponseBody
    public String[] findComment(@RequestParam("url") String url, @RequestParam("keyword") String keyword){
        String baseUrl = "http://localhost:5000/find?url=" + url+"&keyword="+keyword;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String[]> response = restTemplate.getForEntity(baseUrl, String[].class);
        String[] result = response.getBody();
        return result;
    }

    @GetMapping("/find/{url}")
    public String find(@PathVariable String url, Model model){
        model.addAttribute("url",url);
        return "find";
    }

    @GetMapping("/getVideoInformation/{url}")
    public VideoInformation getVideoInformation(@PathVariable String url, Model model){
        String baseUrl = "http://localhost:5000/getVideoInformation?url=" + url;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<VideoInformation> response = restTemplate.getForEntity(baseUrl, VideoInformation.class);
        VideoInformation videoInformation = response.getBody();
        return videoInformation;
    }

//    @GetMapping("/interest/{url}")
//    public Interest[] get_interest(@PathVariable String url){
//        String baseUrl = "http://localhost:5000/interest?url=" + url;
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<Interest[]> response = restTemplate.getForEntity(baseUrl, Interest[].class);
//
//        Interest interests[] = response.getBody();
//
//        return interests;
//    }


    //댓글 분류
    private void classifyComment(Comment[] comments, List<Comment> positiveComments, List<Comment> negativeComments, List<Comment> fearComments,
                                 List<Comment> surprisedComments, List<Comment> angerComments, List<Comment> sadnessComments, List<Comment> neutralComments,
                                 List<Comment> happyComments, List<Comment> disgustComments) {
        for(int i = 0; i< comments.length; i++){    //인덱스 번호를 통해서 긍정, 부정 , 감정 댓글 분류
            if(comments[i].getIndex().equals("1")) {
                positiveComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("2")){
                fearComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("3")){
                surprisedComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("4")){
                angerComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("5")){
                sadnessComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("6")){
                neutralComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("7")){
                happyComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("8")){
                disgustComments.add(comments[i]);
            }
            else if(comments[i].getIndex().equals("0")){
                negativeComments.add(comments[i]);
            }
        }
    }

    @GetMapping("/loading")
    public String loading(){

        return "loading";
    }
}


