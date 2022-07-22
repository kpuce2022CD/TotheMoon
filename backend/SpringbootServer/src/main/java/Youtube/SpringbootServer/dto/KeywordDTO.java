package Youtube.SpringbootServer.dto;

import Youtube.SpringbootServer.entity.Keyword;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
public class KeywordDTO {
    private String [] b5 ;
    private String [][] comments;

    //DTO->Entity
    public Keyword toEntity(int i){
        return new Keyword(i,b5[i]);
    }

    //Response DTO
    @Getter
    public static class Response{

        private int keywordRank;
        private String word;

        //Entity -> DTO
        public Response(Keyword keyword){
            this.keywordRank = keyword.getKeywordRank();
            this.word = keyword.getWord();
        }
    }
}
