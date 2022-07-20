package Youtube.SpringbootServer.entity;

import Youtube.SpringbootServer.dto.InterestDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Interest {

    @Id
    @GeneratedValue
    @Column(name = "interest_id")
    private Long id;

    @ManyToOne( fetch = LAZY)
    @JoinColumn(name= "record_id")
    private Record record;

    private String commentDate;

    private String commentCount;

    public Interest(){

    }

    public Interest(String commentDate, String commentCount) {
        this.commentDate = commentDate;
        this.commentCount = commentCount;
    }

    public void addRecord(Record record){
        this.record = record;
        record.getInterests().add(this);
    }
}
