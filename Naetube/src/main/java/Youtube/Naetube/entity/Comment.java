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

    public Comment(String index, String id, String comment, String date, String num_like) {
        this.index = index;
        this.id = id;
        this.comment = comment;
        this.date = date;
        this.num_like = num_like;
    }
}
