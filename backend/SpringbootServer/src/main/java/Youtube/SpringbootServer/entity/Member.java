package Youtube.SpringbootServer.entity;

import Youtube.SpringbootServer.dto.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Record> records = new ArrayList<>();

    public Member(){

    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
