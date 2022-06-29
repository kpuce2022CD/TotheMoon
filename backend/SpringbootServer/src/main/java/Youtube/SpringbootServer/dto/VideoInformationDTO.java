package Youtube.SpringbootServer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoInformationDTO {
    private String title;
    private String date;
    private String view;
    private String like;

}
