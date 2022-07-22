package Youtube.SpringbootServer.dto;
import Youtube.SpringbootServer.entity.Interest;
import Youtube.SpringbootServer.entity.Timeline;
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

    //DTO->Entity
    public Timeline toEntity(){     //DTO -> 엔티티
        return new Timeline(count,sec,label);
    }

    //Response DTO
    @Getter
    public static class Response{

        private String count;
        private int sec;
        private String label;

        //Entity -> DTO
        public Response(Timeline timeline){
            this.count = timeline.getCount();
            this.sec = timeline.getSec();
            this.label = timeline.getLabel();
        }
    }
}
