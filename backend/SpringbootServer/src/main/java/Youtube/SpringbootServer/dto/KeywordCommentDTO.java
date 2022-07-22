package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Keyword;
import Youtube.SpringbootServer.entity.KeywordComment;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class KeywordCommentDTO {

    public KeywordComment toEntity(KeywordDTO keywordDTO, int i, int j){
        return new KeywordComment(keywordDTO.getComments()[i][j]);
    }

    //Response DTO
    @Getter
    public static class Response{

        private String content;
        private int keywordRank;

        //Entity -> DTO
        public Response(KeywordComment keywordComment){
            this.content = keywordComment.getContent();
            this.keywordRank = keywordComment.getKeyword().getKeywordRank();
        }
    }

}
