package illvent.backend.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDTO {

    @JsonProperty
    private Long no;

    @JsonProperty
    private String email;

    @JsonProperty
    private String name;

    @JsonProperty
    private String location;

    public MemberResponseDTO(Member member) {
        this.no = member.getNo();
        this.email = member.getEmail();
        this.name = member.getName();
        this.location = member.getLocation();
    }
}
