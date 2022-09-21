package Youtube.SpringbootServer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class VideoInformation {

    @Id
    @GeneratedValue
    @Column(name = "videoInfo_id")
    private Long id;

    @OneToOne( fetch = LAZY)
    @JoinColumn(name= "record_id")
    private Record record;

    private String title;

    private String videoDate;

    private String view;

    private String videoLike;

    public VideoInformation() {
    }

    public VideoInformation(String title, String videoDate, String view, String videoLike) {
        this.title = title;
        this.videoDate = videoDate;
        this.view = view;
        this.videoLike = videoLike;
    }

    public void addRecord(Record record){
        this.record = record;
        record.setVideoInformation(this);
    }
}
