package Youtube.SpringbootServer.service;

import Youtube.SpringbootServer.dto.VideoInformationDTO;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class VideoService {

    //조회수 포맷팅
    public String viewData(VideoInformationDTO[] videoInformation){
        DecimalFormat decFormat = new DecimalFormat("###,###");
        int view = Integer.parseInt(videoInformation[0].getView());

        return decFormat.format(view);
    }

    //게시일 포맷팅
    public String dateData(VideoInformationDTO[] videoInformation){

        return videoInformation[0].getDate().replace('-','.');
    }
}
