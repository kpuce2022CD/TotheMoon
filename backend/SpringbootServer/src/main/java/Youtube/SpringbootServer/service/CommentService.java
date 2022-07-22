package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.CommentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class CommentService {

    private List<CommentDTO> positiveComments = new ArrayList<>(); // json 구분 인덱스 : 1
    private List<CommentDTO> negativeComments = new ArrayList<>(); // json 구분 인덱스 : 0
    private List<CommentDTO> fearComments = new ArrayList<>(); // json 구분 인덱스 : 2
    private List<CommentDTO> surprisedComments = new ArrayList<>(); // json 구분 인덱스 : 3
    private List<CommentDTO> angerComments = new ArrayList<>(); // json 구분 인덱스 : 4
    private List<CommentDTO> sadnessComments = new ArrayList<>(); // json 구분 인덱스 : 5
    private List<CommentDTO> neutralComments = new ArrayList<>(); // json 구분 인덱스 : 6
    private List<CommentDTO> happyComments = new ArrayList<>(); // json 구분 인덱스 : 7
    private List<CommentDTO> disgustComments = new ArrayList<>(); // json 구분 인덱스 : 8

    //댓글 분류
    public HashMap<String, List> classifyComment(CommentDTO[] comments){

        HashMap<String, List> commentMap = new HashMap<>();

        clear();

        for(int i = 0; i< comments.length; i++){    //인덱스 번호를 통해서 긍정, 부정 , 감정 댓글 분류
            String index = comments[i].getIndex();
            switch (index){
                case "0" :
                    negativeComments.add(comments[i]);
                    break;
                case "1" :
                    positiveComments.add(comments[i]);
                    break;
                case "2" :
                    fearComments.add(comments[i]);
                    break;
                case "3" :
                    surprisedComments.add(comments[i]);
                    break;
                case "4" :
                    angerComments.add(comments[i]);
                    break;
                case "5" :
                    sadnessComments.add(comments[i]);
                    break;
                case "6" :
                    neutralComments.add(comments[i]);
                    break;
                case "7" :
                    happyComments.add(comments[i]);
                    break;
                case "8" :
                    disgustComments.add(comments[i]);
                    break;
            }

        }
        log.info("positiveComments = {}",positiveComments);
        //[Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0) , ...]
        log.info("positiveComments[0] = {}",positiveComments.get(0));
        //Comment(index=1, id=상휘1퍼센트, comment=첫곡 미쳤다, date=2022-01-19T04:29:36Z, num_like=0)
        log.info("전체 댓글 수 = {}",comments.length);

        commentMap.put("positiveComments",positiveComments);
        commentMap.put("negativeComments",negativeComments);
        commentMap.put("fearComments",fearComments);
        commentMap.put("surprisedComments",surprisedComments);
        commentMap.put("angerComments",angerComments);
        commentMap.put("sadnessComments",sadnessComments);
        commentMap.put("neutralComments",neutralComments);
        commentMap.put("happyComments",happyComments);
        commentMap.put("disgustComments",disgustComments);

        return commentMap;
    }

    //긍정부정 퍼센트 계산
    public HashMap<String, Double> positiveNegativePercent(){

        HashMap<String, Double> positiveNegativePercentMap = new HashMap<>();

        double positivePercent = ((double)positiveComments.size() / ((double)positiveComments.size()+(double)negativeComments.size()))*100;
        double negativePercent = ((double)negativeComments.size() / ((double)positiveComments.size()+(double)negativeComments.size()))*100;

        double refined_positivePercent = Double.parseDouble(String.format("%.1f",positivePercent));
        double refined_negativePercent = Double.parseDouble(String.format("%.1f",negativePercent));

        positiveNegativePercentMap.put("refined_positivePercent",refined_positivePercent);
        positiveNegativePercentMap.put("refined_negativePercent",refined_negativePercent);

        log.info("긍정 댓글 수 = {}", positiveComments.size());
        log.info("부정 댓글 수 = {}", negativeComments.size());
        log.info("긍정 댓글 비율 = {}", positivePercent);
        log.info("부정 댓글 비율 = {}", negativePercent);

        return positiveNegativePercentMap;
    }

    //감정 분석 퍼센트 계산
    public HashMap<String, Double> sentimentPercent(){

        HashMap<String, Double> sentimentPercentMap = new HashMap<>();

        double emTotalSize = fearComments.size()+ surprisedComments.size()+ angerComments.size()+ sadnessComments.size()+ neutralComments.size()+ happyComments.size()+ disgustComments.size();
        double fearPercent = (((double)fearComments.size()/emTotalSize)*100);
        double surprisedPercent = ((double)surprisedComments.size()/emTotalSize)*100;
        double angerPercent = ((double)angerComments.size()/emTotalSize)*100;
        double sadnessPercent = ((double)sadnessComments.size()/emTotalSize)*100;
        double neutralPercent = ((double)neutralComments.size()/emTotalSize)*100;
        double happyPercent = ((double)happyComments.size()/emTotalSize)*100;
        double disgustPercent = ((double)disgustComments.size()/emTotalSize)*100;

        // 소수점 정규화
        double refined_fearPercent = Double.parseDouble(String.format("%.1f",fearPercent));
        double refined_surprisedPercent = Double.parseDouble(String.format("%.1f",surprisedPercent));
        double refined_angerPercent = Double.parseDouble(String.format("%.1f",angerPercent));
        double refined_sadnessPercent = Double.parseDouble(String.format("%.1f",sadnessPercent));
        double refined_neutralPercent = Double.parseDouble(String.format("%.1f",neutralPercent));
        double refined_happyPercent = Double.parseDouble(String.format("%.1f",happyPercent));
        double refined_disgustPercent = Double.parseDouble(String.format("%.1f",disgustPercent));

        log.info("공포 댓글 비율 = {}", refined_fearPercent);
        log.info("놀람 댓글 비율 = {}", refined_surprisedPercent);
        log.info("분노 댓글 비율 = {}", refined_angerPercent);
        log.info("슬픔 댓글 비율 = {}", refined_sadnessPercent);
        log.info("중립 댓글 비율 = {}", refined_neutralPercent);
        log.info("행복 댓글 비율 = {}", refined_happyPercent);
        log.info("혐오 댓글 비율 = {}", refined_disgustPercent);

        sentimentPercentMap.put("refined_fearPercent",refined_fearPercent);
        sentimentPercentMap.put("refined_surprisedPercent", refined_surprisedPercent);
        sentimentPercentMap.put("refined_angerPercent",refined_angerPercent);
        sentimentPercentMap.put("refined_sadnessPercent",refined_sadnessPercent);
        sentimentPercentMap.put("refined_neutralPercent",refined_neutralPercent);
        sentimentPercentMap.put("refined_happyPercent",refined_happyPercent);
        sentimentPercentMap.put("refined_disgustPercent",refined_disgustPercent);

        return sentimentPercentMap;
    }

    public void clear(){
        positiveComments.clear();
        negativeComments.clear();
        fearComments.clear();
        surprisedComments.clear();
        angerComments.clear();
        sadnessComments.clear();
        neutralComments.clear();
        happyComments.clear();
        disgustComments.clear();
    }
}
