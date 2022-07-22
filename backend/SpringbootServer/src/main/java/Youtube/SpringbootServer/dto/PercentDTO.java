package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Percent;
import Youtube.SpringbootServer.entity.Timeline;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PercentDTO {

    private Double positive;
    private Double negative;
    private Double happy;
    private Double surprise;
    private Double anger;
    private Double sadness;
    private Double neutral;
    private Double disgust;
    private Double fear;

    public void SetPercentDTO(Double positive, Double negative, Double happy, Double surprise, Double anger, Double sadness,Double neutral, Double disgust, Double fear) {
        this.positive = positive;
        this.negative = negative;
        this.happy = happy;
        this.surprise = surprise;
        this.anger = anger;
        this.sadness = sadness;
        this.neutral = neutral;
        this.disgust = disgust;
        this.fear = fear;
    }

    //DTO->Entity
    public Percent toEntity(){     //DTO -> 엔티티
        return new Percent(positive, negative, happy, surprise, anger, sadness, neutral,disgust, fear);
    }

    //Response DTO
    @Getter
    public static class Response{

        private Double positive;
        private Double negative;
        private Double happy;
        private Double surprise;
        private Double anger;
        private Double sadness;
        private Double neutral;
        private Double disgust;
        private Double fear;

        //Entity -> DTO
        public Response(Percent percent){
            this.positive = percent.getPositive();
            this.negative = percent.getNegative();
            this.happy = percent.getHappy();
            this.surprise = percent.getSurprise();
            this.anger = percent.getAnger();
            this.sadness = percent.getSadness();
            this.neutral = percent.getNeutral();
            this.disgust = percent.getDisgust();
            this.fear = percent.getFear();
        }
    }
}
