package Youtube.SpringbootServer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Percent {

    @Id
    @GeneratedValue
    @Column(name = "percent_id")
    private Long id;

    @OneToOne( fetch = LAZY)
    @JoinColumn(name= "record_id")
    private Record record;

    private Double positive;
    private Double negative;
    private Double happy;
    private Double surprise;
    private Double anger;
    private Double sadness;
    private Double neutral;
    private Double disgust;
    private Double fear;


    public Percent(){

    }

    public Percent(Double positive, Double negative, Double happy, Double surprise, Double anger, Double sadness, Double neutral, Double disgust, Double fear) {
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

    public void addRecord(Record record){
        this.record = record;
        record.setPercent(this);
    }
}
