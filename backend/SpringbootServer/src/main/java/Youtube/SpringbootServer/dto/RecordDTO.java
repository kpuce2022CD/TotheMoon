package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Record;
import lombok.Getter;

@Getter
public class RecordDTO {

    private Long id;
    private String title;
    private String createDate;

    public RecordDTO(Long id, String title, String createDate) {
        this.id = id;
        this.title = title;
        this.createDate = createDate;
    }

    //Entity -> DTO
    public RecordDTO(Record record){
        this.id = record.getId();
        this.title = record.getVideoInformation().getTitle();
        this.createDate = record.getCreateDate();
    }

}
