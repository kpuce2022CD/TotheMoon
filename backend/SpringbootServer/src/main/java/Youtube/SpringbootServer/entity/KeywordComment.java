package Youtube.SpringbootServer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class KeywordComment {

    @Id
    @GeneratedValue
    @Column(name = "keyword_comment_id")
    private Long id;

    //외래키
    @ManyToOne( fetch = LAZY)
    @JoinColumn(name= "keyword_id")
    private Keyword keyword;

    //댓글 내용
    @Column(length = 1000)
    private String content;

    public KeywordComment(String content) {
        this.content = content;
    }
}
