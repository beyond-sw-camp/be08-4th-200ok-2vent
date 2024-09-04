package illvent.backend.event.domain;

import illvent.backend.wish.domain.Wish;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "Event")
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String address;

    private LocalDate eventDate;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, length = 2047)
    private String description;

    private String region;

    private int price;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private int likes;

    private boolean online;

    private boolean offline;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @OneToMany(mappedBy = "event")
    private List<Wish> wishes = new ArrayList<>();

    public void updateViews(){
        this.views++;
    }

    public void updateLikesIncrease(){
        this.likes++;
    }

    public void updateLikesDecrease(){
        this.likes--;
    }
}
