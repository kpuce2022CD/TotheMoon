package Youtube.SpringbootServer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class VideoInformationDTO {
    private String title;
    private String date;
    private String view;
    private String like;

    public void setVideoInfo(String title,String date,String view,String like){
        this.title =title;
        this.date =date;
        this.view =view;
        this.like=like;
    }
}
