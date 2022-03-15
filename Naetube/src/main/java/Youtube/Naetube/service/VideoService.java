package Youtube.Naetube.service;

import Youtube.Naetube.domain.VideoInformation;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class VideoService {

    //조회수 포맷팅
    public String viewData(VideoInformation[] videoInformation){
        DecimalFormat decFormat = new DecimalFormat("###,###");
        int view = Integer.parseInt(videoInformation[0].getView());

        return decFormat.format(view);
    }

    //게시일 포맷팅
    public String dateData(VideoInformation[] videoInformation){

        return videoInformation[0].getDate().replace('-','.');
    }
}
