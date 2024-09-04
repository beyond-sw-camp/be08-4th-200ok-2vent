package illvent.backend.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDTO {

    @JsonProperty
    private Long no;

    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    @JsonProperty
    private String region;

    @JsonProperty
    private Long memberNo;

    @JsonProperty
    private LocalDateTime createDate;

    @JsonProperty
    private LocalDateTime updateDate;

    @JsonProperty
    private int views;

    @JsonProperty
    private int likes;

    public PostResponseDTO(Post post) {
        this.no = post.getNo();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.region = post.getRegion();
        this.memberNo = post.getMember().getNo();
        this.createDate = post.getCreateDate();
        this.updateDate = post.getUpdateDate();
        this.views = post.getViews();
        this.likes = post.getLikes();
    }
}