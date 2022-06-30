package Youtube.SpringbootServer.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimelineDTO {
    private String count;
    private int sec;
    private String label;
}
