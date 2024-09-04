package illvent.backend.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponseDTO {

    @JsonProperty
    private Long no;

    @JsonProperty
    private String title;

    @JsonProperty
    private String location;

    @JsonProperty
    private String address;

    @JsonProperty
    private LocalDate eventDate;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private String description;

    @JsonProperty
    private String region;

    @JsonProperty
    private int price;

    @JsonProperty
    private int views;

    @JsonProperty
    private int likes;

    @JsonProperty
    private boolean online;

    @JsonProperty
    private boolean offline;

    @JsonProperty
    private Double x;

    @JsonProperty
    private Double y;

    public EventResponseDTO(Event event){
        this.no = event.getNo();
        this.title = event.getTitle();
        this.location = event.getLocation();
        this.address = event.getAddress();
        this.eventDate = event.getEventDate();
        this.imageUrl = event.getImageUrl();
        this.description = event.getDescription();
        this.region = event.getRegion();
        this.price = event.getPrice();
        this.views = event.getViews();
        this.likes = event.getLikes();
        this.online = event.isOnline();
        this.offline = event.isOffline();
        this.x = event.getX();
        this.y = event.getY();
    }
}
