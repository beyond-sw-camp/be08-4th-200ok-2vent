package illvent.backend.wish.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class WishDeleteRequestDTO {

    @NotNull
    private Long memberNo;

    @NotNull
    private Long eventNo;
}
