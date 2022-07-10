package Youtube.SpringbootServer.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class MemberDTO {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
