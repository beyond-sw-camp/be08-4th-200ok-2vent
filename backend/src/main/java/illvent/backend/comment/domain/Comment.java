package illvent.backend.comment.domain;

import illvent.backend.comment.dto.CommentUpdateRequestDTO;
import illvent.backend.member.domain.Member;
import illvent.backend.post.domain.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "Comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false)
    private Long level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no", nullable = false)
    private Post post;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    public void updateStatus() {
        this.status = CommentStatus.N;
    }

    public void update(CommentUpdateRequestDTO commentUpdateRequestDTO) {
        Optional.ofNullable(commentUpdateRequestDTO.getContent()).ifPresent(content ->this.content = content);
        this.updateDate = LocalDateTime.now();
    }
}
