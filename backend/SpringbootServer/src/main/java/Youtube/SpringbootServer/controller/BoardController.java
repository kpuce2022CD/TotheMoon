package Youtube.SpringbootServer.controller;

import Youtube.SpringbootServer.SessionConst;
import Youtube.SpringbootServer.dto.*;
import Youtube.SpringbootServer.entity.*;
import Youtube.SpringbootServer.service.BoardService;
import Youtube.SpringbootServer.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentListDTO commentListDTO;
    private final KeywordDTO keywordDTO;
    private final PercentDTO percentDTO;
    private final VideoInformationDTO videoInformationDTO;
    private final InterestListDTO interestListDTO;
    private final TimeLineListDTO timeLineListDTO;
    private final MemberService memberService;

    //목록 조회
    @GetMapping("/list")
    public String recordList(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        List<Record> records = boardService.findRecords(loginMember.getId());
        model.addAttribute("records", records);
        return "db_complete_list";
    }

    //저장
    @GetMapping("/persist")
    public String persistComment(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember){
        Record record = new Record();
        boardService.registerDB(commentListDTO,record,loginMember,keywordDTO,percentDTO, videoInformationDTO,interestListDTO,timeLineListDTO);
        return "redirect:/list";
    }

    //분석 1건 조회
    @GetMapping("/persist/{recordId}")
    public String showComments(@PathVariable String recordId,  Model model){
        long longRecordId = Long.parseLong(recordId);
        List<Comment> comments = boardService.findComment(longRecordId);
        Percent percent = boardService.findPercent(longRecordId);
        VideoInformation videoInfo = boardService.findVideoInfo(longRecordId);
        List<Interest> interest = boardService.findInterest(longRecordId);
        List<Keyword> keyword = boardService.findKeyword(longRecordId);
        List<Timeline> timeLine = boardService.findTimeLine(longRecordId);
        List<KeywordComment> keywordComments = boardService.findKeywordComment(longRecordId);
        model.addAttribute("comments",comments);
        model.addAttribute("percent",percent);
        model.addAttribute("videoInfo",videoInfo);
        model.addAttribute("interests",interest);
        model.addAttribute("keywords",keyword);
        model.addAttribute("timelines",timeLine);
        model.addAttribute("keywordComments", keywordComments);
        return "db_complete_show";
    }

    //분석 1건 삭제
    @GetMapping("/delete/{recordId}")
    public String delete(@PathVariable String recordId){
        long longRecordId = Long.parseLong(recordId);
        boardService.delete(longRecordId);
        return "redirect:/list";
    }
}
