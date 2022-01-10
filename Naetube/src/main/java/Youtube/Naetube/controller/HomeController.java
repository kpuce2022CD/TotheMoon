package Youtube.Naetube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

    //홈 화면
    @GetMapping("/")
    public String Home(){
        return "home";
    }

    //결과 화면
    @GetMapping("/search/{url}")
    public String Search(@PathVariable String url, Model model){
        String baseUrl = "http://localhost:5000?url=" + url;                   //flask 접속 url
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(baseUrl, String.class);     //flask app.py의 success 리턴해서 response에 저장.
        System.out.println(response);
        model.addAttribute("url", "https://www.youtube.com/embed/"+url);    //search.html에 url 전달.
        return "search";
    }

}
