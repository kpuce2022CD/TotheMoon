package Youtube.Naetube.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VideoInformation {
    private String title;
    private String date;
    private String view;
    private String like;

    public VideoInformation(){

    }
}
