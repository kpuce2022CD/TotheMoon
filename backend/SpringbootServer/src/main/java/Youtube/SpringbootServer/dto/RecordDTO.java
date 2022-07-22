package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Record;
import Youtube.SpringbootServer.entity.Timeline;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
public class RecordDTO {

    private Long id;
    private String title;
    private String createDate;

    //Entity -> DTO
    public RecordDTO(Record record){
        this.id = record.getId();
        this.title = record.getVideoInformation().getTitle();
        this.createDate = record.getCreateDate();
    }

}
