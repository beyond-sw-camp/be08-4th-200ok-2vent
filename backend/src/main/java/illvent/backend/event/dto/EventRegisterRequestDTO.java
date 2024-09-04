package illvent.backend.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class EventRegisterRequestDTO {

    @NotNull
    private String title;

    @NotNull
    private String location;

    @NotNull
    private String address;

    private LocalDate eventDate;

    @NotNull
    private String imageUrl;

    @NotNull
    private String description;

    private String region;

    private int price;

    @NotNull
    private int views;

    @NotNull
    private int likes;

    private boolean online;

    private boolean offline;

    @NotNull
    private Double x;

    @NotNull
    private Double y;
}
