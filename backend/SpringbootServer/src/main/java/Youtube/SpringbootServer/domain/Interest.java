package Youtube.SpringbootServer.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Interest {
    private String commentDate;
    private String commentCount;

    public Interest(@JsonProperty("commentDate") String commentDate, @JsonProperty("commentCount") String commentCount) {
        this.commentDate = commentDate;
        this.commentCount = commentCount;
    }
}
