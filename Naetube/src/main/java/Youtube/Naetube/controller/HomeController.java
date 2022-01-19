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

        ResponseEntity<Comment[]> response =
                restTemplate.getForEntity(baseUrl, Comment[].class);

        Comment comments[] = response.getBody();
        List<Comment> positivecomments = new ArrayList<>();
        List<Comment> negativecomments = new ArrayList<>();

        for(int i=0;i<comments.length;i++){
            if(comments[i].getIndex().equals("1")){
                positivecomments.add(comments[i]);
            }
            else{
                negativecomments.add(comments[i]);
            }
        }
        for(int i=0;i<positivecomments.size();i++){
            System.out.println(positivecomments.get(i).getIndex());
            System.out.println(positivecomments.get(i).getId());
            System.out.println(positivecomments.get(i).getComment());
            System.out.println(positivecomments.get(i).getDate());
            System.out.println(positivecomments.get(i).getNum_like());
        }
        for(int i=0;i<negativecomments.size();i++){
            System.out.println(negativecomments.get(i).getIndex());
            System.out.println(negativecomments.get(i).getId());
            System.out.println(negativecomments.get(i).getComment());
            System.out.println(negativecomments.get(i).getDate());
            System.out.println(negativecomments.get(i).getNum_like());
        }
        model.addAttribute("url", "https://www.youtube.com/embed/"+url);    //search.html에 url 전달.
        return "search";
    }

    @GetMapping("/test")
    public String Test(Model model){
        String baseUrl = "http://localhost:5000/tospring";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String[]> response =
                restTemplate.getForEntity(baseUrl, String[].class);

        String test[] = response.getBody();
        System.out.println(test[0]);
        System.out.println(test[1]);
        System.out.println(test[2]);
        System.out.println(test[3]);
        System.out.println(test[4]);

        model.addAttribute("data", "response");    //search.html에 url 전달.

        return "test";
    }

    @GetMapping("/test2/{url}")
    public String Test2(@PathVariable String url, Model model){
        String baseUrl = "http://localhost:5000/tospring2?url=" + url;
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Comment[]> response =
                restTemplate.getForEntity(baseUrl, Comment[].class);

        Comment comments[] = response.getBody();
        List<Comment> positivecomments = new ArrayList<>();
        List<Comment> negativecomments = new ArrayList<>();

        for(int i=0;i<comments.length;i++){
            if(comments[i].getIndex().equals("1")){
                positivecomments.add(comments[i]);
            }
            else{
                negativecomments.add(comments[i]);
            }
        }
        for(int i=0;i<positivecomments.size();i++){
            System.out.println(positivecomments.get(i).getIndex());
            System.out.println(positivecomments.get(i).getId());
            System.out.println(positivecomments.get(i).getComment());
            System.out.println(positivecomments.get(i).getDate());
            System.out.println(positivecomments.get(i).getNum_like());
        }
        for(int i=0;i<positivecomments.size();i++){
            System.out.println(negativecomments.get(i).getIndex());
            System.out.println(negativecomments.get(i).getId());
            System.out.println(negativecomments.get(i).getComment());
            System.out.println(negativecomments.get(i).getDate());
            System.out.println(negativecomments.get(i).getNum_like());
        }



//        System.out.println(positiveComments[i].getId());
//            System.out.println(positiveComments[i].getComment());
//            System.out.println(positiveComments[i].getDate());
//            System.out.println(positiveComments[i].getNum_like());


        model.addAttribute("data", "response");    //search.html에 url 전달.

        return "test";
    }




}


