package illvent.backend.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostUpdateRequestDTO {

    @NotNull
    public String title;

    @NotNull
    private String content;

    @NotNull
    private String region;

    @NotNull
    private int likes;

}
