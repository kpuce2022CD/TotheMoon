package Youtube.SpringbootServer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="analysis_comment")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne( fetch = LAZY)
    @JoinColumn(name= "record_id")
    private Record record;

    //댓글 작성자 아이디
    private String commentUserId;

    //댓글 작성 날짜
    private String commentDate;

    //댓글 종류
    private String commentIndex;

    //댓글 내용
    @Column(length = 2000)
    private String content;

    private String commentLike;

//    private Percent percent;

    public Comment(String commentUserId, String date, String index, String content, String commentLike) {
        this.commentUserId = commentUserId;
        this.commentDate = date;
        this.commentIndex = index;
        this.content = content;
        this.commentLike = commentLike;
    }

    public void addRecord(Record record){
        this.record = record;
    }
}
