package illvent.backend.wish.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.wish.domain.Wish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishResponseDTO {

    @JsonProperty
    private Long no;

    @JsonProperty
    private Long memberNo;

    @JsonProperty
    private Long eventNo;

    public WishResponseDTO(Wish wish){
        this.no = wish.getNo();
        this.memberNo = wish.getMember().getNo();
        this.eventNo = wish.getEvent().getNo();
    }
}
