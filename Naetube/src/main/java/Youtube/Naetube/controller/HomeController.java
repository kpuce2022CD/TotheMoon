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
        List<Comment> positiveComments = new ArrayList<>();
        List<Comment> negativeComments = new ArrayList<>();

        for(int i=0;i<comments.length;i++){    //인덱스 번호를 통해서 긍정, 부정 댓글 분류
            if(comments[i].getIndex().equals("1")){
                positiveComments.add(comments[i]);
            }
            else{
                negativeComments.add(comments[i]);
            }
        }
        System.out.println("positiveComments = " + positiveComments);
        //[Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0) , ...]
        System.out.println("positiveComments[0] = " + positiveComments.get(0));
        //Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0)
//        for(int i=0;i<positiveComments.size();i++){
//            System.out.println(positiveComments.get(i).getIndex());
//            System.out.println(positiveComments.get(i).getId());
//            System.out.println(positiveComments.get(i).getComment());
//            System.out.println(positiveComments.get(i).getDate());
//            System.out.println(positiveComments.get(i).getNum_like());
//        }
        double positivePercent = (double)positiveComments.size() / (double)comments.length * 100;
        double negativePercent = (double)negativeComments.size() / (double)comments.length * 100;

        System.out.println("전체 댓글 수 = " + comments.length);
        System.out.println("긍정 댓글 수 = " + positiveComments.size());
        System.out.println("부정 댓글 수  = " + negativeComments.size());
        System.out.println("긍정 댓글 비율 = " + positivePercent);
        System.out.println("부정 댓글 비율 = " + negativePercent);

        model.addAttribute("url", "https://www.youtube.com/embed/"+url);    //search.html에 url 전달.
        model.addAttribute("positiveComments",positiveComments);
        model.addAttribute("negativeComments",negativeComments);
        model.addAttribute("comments",comments);
        model.addAttribute("positivePercent", positivePercent);
        model.addAttribute("negativePercent", negativePercent);
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


}


