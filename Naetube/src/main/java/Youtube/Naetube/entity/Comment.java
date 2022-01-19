package Youtube.Naetube.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//comment Dto

@Getter
@Setter
@ToString
public class Comment {
    private String index;
    private String id;
    private String comment;
    private String date;
    private String num_like;
}
