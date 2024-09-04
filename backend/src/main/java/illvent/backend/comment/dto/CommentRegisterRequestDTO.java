package illvent.backend.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CommentRegisterRequestDTO {

    @NotNull
    private String content;

    @NotNull
    private Long level;

    @NotNull
    private Long memberNo;

}
