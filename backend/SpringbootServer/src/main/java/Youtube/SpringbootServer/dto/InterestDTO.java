package Youtube.SpringbootServer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InterestDTO {
    private String commentDate;
    private String commentCount;

    public InterestDTO(@JsonProperty("commentDate") String commentDate, @JsonProperty("commentCount") String commentCount) {
        this.commentDate = commentDate;
        this.commentCount = commentCount;
    }
}
