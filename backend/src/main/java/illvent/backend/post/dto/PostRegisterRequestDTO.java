package illvent.backend.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PostRegisterRequestDTO {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String region;

}
