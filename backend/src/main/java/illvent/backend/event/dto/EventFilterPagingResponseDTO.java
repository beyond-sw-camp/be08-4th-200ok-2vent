package illvent.backend.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class EventFilterPagingResponseDTO {

    private int totalPages; // 전체 페이지 개수
    private Long totalElements; // 전체 데이터 개수
    private int pageNumber; // 현재 페이지 번호
    private int pageSize; // 페이지 크기
    private List<EventInfoResponseDTO> contents;
}
