package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Comment;
import Youtube.SpringbootServer.entity.VideoInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class VideoInformationDTO {
    private String title;
    private String date;
    private String view;
    private String like;

    public void setVideoInfo(String title,String date,String view,String like){
        this.title =title;
        this.date =date;
        this.view =view;
        this.like=like;
    }

    //DTO -> Entity
    public VideoInformation toEntity(){
        return new VideoInformation(title, date, view, like);
    }

    //Response DTO
    @Getter
    public static class Response{

        private String title;
        private String videoDate;
        private String view;
        private String videoLike;

        //Entity -> DTO
        public Response(VideoInformation videoInformation){
            this.title = videoInformation.getTitle();
            this.videoDate = videoInformation.getVideoDate();
            this.view = videoInformation.getView();
            this.videoLike = videoInformation.getVideoLike();
        }
    }

}
