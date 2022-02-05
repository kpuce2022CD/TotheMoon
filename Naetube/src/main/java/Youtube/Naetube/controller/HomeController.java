package Youtube.Naetube.controller;

import Youtube.Naetube.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private ObjectMapper objectMapper = new ObjectMapper();
    //홈 화면
    @GetMapping("/")
    public String Home(){
        return "home";
    }

    //결과 화면
    @GetMapping("/search/{url}")
    public String Search(@PathVariable String url, Model model){
        String baseUrl = "http://localhost:5000/tospring2?url=" + url;
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
        return "search";
    }

    @GetMapping("/keyword/{url}")
    public String getKeyword(@PathVariable String url, Model model){
        String baseUrl = "http://localhost:5000/searchKeyword?url=" + url;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Keyword> response = restTemplate.getForEntity(baseUrl, Keyword.class);

        Keyword keyword = response.getBody();

        System.out.println(keyword.getB5()[0]);
        System.out.println(keyword.getComments()[0][0]);
        return "keyword";
    }

    @GetMapping("/timeline")
    public String getTimeline(Model model){
        String baseUrl = "http://localhost:5000/timeline";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Timeline[]> response = restTemplate.getForEntity(baseUrl, Timeline[].class);
        Timeline[] timeline = response.getBody();
        List<Timeline> one_timeline_comments = new ArrayList<>();
        List<Timeline> many_timeline_comments = new ArrayList<>();
        for(int i=0; i<timeline.length; i++){
            if(timeline[i].getLength()>1){
                many_timeline_comments.add(timeline[i]);
            } else{
                one_timeline_comments.add(timeline[i]);
            }
        }
        model.addAttribute("many_timeline_comments",many_timeline_comments);
        model.addAttribute("one_timeline_comments",one_timeline_comments);
        model.addAttribute("url","5qcUbf_lSJ4");
        return "timeline";
    }


    //댓글 분류
    private void classifyComment(Comment[] comments, List<Comment> positiveComments, List<Comment> negativeComments, List<Comment> fearComments,
                                 List<Comment> surprisedComments, List<Comment> angerComments, List<Comment> sadnessComments, List<Comment> neutralComments,
                                 List<Comment> happyComments, List<Comment> disgustComments) {
        for(int i = 0; i< comments.length; i++){    //인덱스 번호를 통해서 긍정, 부정 , 감정 댓글 분류
            if(comments[i].getIndex().equals("1")){
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
}


