package Youtube.SpringbootServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Record> records = new ArrayList<>();

    @Column(name="register_date")
    @CreatedDate
    private String registerDate;

    @PrePersist
    public void onPrePersist(){
        this.registerDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd a KK : mm"));
    }

    public Member(){

    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
