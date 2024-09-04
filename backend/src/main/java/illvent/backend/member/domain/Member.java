package illvent.backend.member.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import illvent.backend.comment.domain.Comment;
import illvent.backend.post.domain.Post;
import illvent.backend.member.dto.MemberUpdateRequestDTO;
import illvent.backend.wish.domain.Wish;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Member")
@Data
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(name = "pw" ,nullable = false, length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, length = 15)
    private String name;

//    @Column(nullable = false, length = 50)
//    private String nickname;

    @Column(nullable = false, length = 20)
    private String location;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Wish> wishes = new ArrayList<>();

    public void update(MemberUpdateRequestDTO memberUpdateRequestDTO) {
        Optional.ofNullable(memberUpdateRequestDTO.getName()).ifPresent(name -> this.name = name);
//        Optional.ofNullable(memberUpdateRequestDTO.getNickname()).ifPresent(nickname -> this.nickname = nickname);
    }

    public void updateStatus() {
        this.status = MemberStatus.N;
    }

    public void updatePassword(String password) {
        Optional.ofNullable(password).ifPresent(name -> this.password = password);
    }
}