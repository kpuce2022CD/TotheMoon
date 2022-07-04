package Youtube.SpringbootServer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Timeline {

    @Id
    @GeneratedValue
    @Column(name = "timeline_id")
    private Long id;

    @ManyToOne( fetch = LAZY)
    @JoinColumn(name= "record_id")
    private Record record;

    private String count;

    private int sec;

    private String label;

    public Timeline() {
    }

    public Timeline(String count, int sec, String label) {
        this.count = count;
        this.sec = sec;
        this.label = label;
    }
}
