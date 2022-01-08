package Youtube.Naetube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home(){
        return "home";
    }

    @GetMapping("/search/{url}")
    public String Search(@PathVariable String url, Model model){
        model.addAttribute("url", "https://www.youtube.com/embed/"+url);
        return "search";
    }

}
