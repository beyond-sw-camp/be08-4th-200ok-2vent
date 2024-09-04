package illvent.backend.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberRegisterRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

//    @NotNull
//    private String nickname;

    @NotNull
    private String location;

//    @NotNull
//    private MemberStatus status;

}
