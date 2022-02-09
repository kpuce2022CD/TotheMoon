package Youtube.Naetube.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public Comment(@JsonProperty("index") String index, @JsonProperty("id") String id,
                   @JsonProperty("comment") String comment, @JsonProperty("date") String date, @JsonProperty("num_like") String num_like) {
        this.index = index;
        this.id = id;
        this.comment = comment;
        this.date = date;
        this.num_like = num_like;
    }
}
