package Youtube.SpringbootServer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

//    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY)
//    private Record record;

    //댓글 작성자 아이디
    private String commentUserId;

    //댓글 작성 날짜
    private String commentDate;

    //댓글 종류
    private String commentIndex;

    //댓글 내용
    @Column(length = 1000)
    private String content;

    private String commentLike;

//    private Percent percent;

    public Comment(){

    }

    public Comment(String commentUserId, String date, String index, String content, String goodnum) {
        this.commentUserId = commentUserId;
        this.commentDate = date;
        this.commentIndex = index;
        this.content = content;
        this.commentLike = goodnum;
    }
}
