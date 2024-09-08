package event.backend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostPageResponseDTO {

    private Long totalDataCount; // 전체 데이터 개수

    private int totalPageCount; // 전체 페이지 개수

    private int currentPageNumber; // 현재 페이지 번호 (0부터 시작)

    private List<PostResponseDTO> posts;

}
