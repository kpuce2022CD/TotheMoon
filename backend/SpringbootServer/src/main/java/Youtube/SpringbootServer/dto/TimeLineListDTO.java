package Youtube.SpringbootServer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TimeLineListDTO {

    private TimelineDTO[] timeline;
}
