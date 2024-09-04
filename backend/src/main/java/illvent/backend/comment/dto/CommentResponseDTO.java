package illvent.backend.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.comment.domain.Comment;
import illvent.backend.comment.domain.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    @JsonProperty
    private Long no;

    @JsonProperty
    private String content;

    @JsonProperty
    private Long level;

    @JsonProperty
    private Long memberNo;

    @JsonProperty
    private Long postNo;

    @JsonProperty
    private LocalDateTime createDate;

    @JsonProperty
    private LocalDateTime updateDate;

    @JsonProperty
    private CommentStatus status;

    public CommentResponseDTO(Comment comment) {
        this.content = comment.getContent();
        this.level = comment.getLevel();
        this.memberNo = comment.getMember().getNo();
        this.postNo = comment.getPost().getNo();
        this.createDate = comment.getCreateDate();
        this.updateDate = comment.getUpdateDate();
        this.status = comment.getStatus();
    }
}
