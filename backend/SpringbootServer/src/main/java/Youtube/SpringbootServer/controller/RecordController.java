package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.dto.CommentDTO;
import Youtube.SpringbootServer.dto.CommentListDTO;
import Youtube.SpringbootServer.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    private final CommentListDTO commentListDTO;

    @GetMapping("/persist/{url}")
    public String persistComment(@PathVariable String url){
        System.out.println("commentDTO = " + commentListDTO.getComments());

        CommentDTO[] comments = commentListDTO.getComments();
        //comment 저장.
        for (CommentDTO comment : comments) {
            recordService.record(comment);
        }

        return "redirect:/search/"+url;
    }
}
