package illvent.backend.event.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class EventInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 제목

    private String description; //

    private String imgUrl; // 이미지 주소

    private int price; // 가격

    private String region; // 지역 (ex.서울/경기/인천)

    private LocalDate eventDate; // 이벤트 진행 날짜

    private boolean online;

    private boolean offline;

    private int views; // 조회수
}
