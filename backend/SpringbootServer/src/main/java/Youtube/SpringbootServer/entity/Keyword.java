package Youtube.SpringbootServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name = "keyword_id")
    private Long id;

    @ManyToOne( fetch = LAZY)
    @JoinColumn(name= "record_id")
    private Record record;

    @JsonIgnore
    @OneToMany(mappedBy = "keyword")
    private List<KeywordComment> keywordComments = new ArrayList<>();

    //순위
    private int keywordRank;

    //키워드
    private String word;

    public Keyword(int keywordRank, String word) {
        this.keywordRank = keywordRank;
        this.word = word;
    }
}
