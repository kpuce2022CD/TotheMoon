package Youtube.SpringbootServer.entity;

import Youtube.SpringbootServer.dto.CommentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Record {

    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "record")
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "record")
    private List<Keyword> keywords = new ArrayList<>();

    @OneToOne(mappedBy = "record", fetch = FetchType.LAZY)
    private Percent percent;

    @OneToOne(mappedBy = "record", fetch = FetchType.LAZY)
    private VideoInformation videoInformation;

    @JsonIgnore
    @OneToMany(mappedBy = "record")
    private List<Interest> interests = new ArrayList<>();

}
