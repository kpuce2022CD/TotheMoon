package Youtube.SpringbootServer.dto;

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
}
