package illvent.backend.comment.service;

import illvent.backend.comment.domain.Comment;
import illvent.backend.comment.domain.CommentRepository;
import illvent.backend.comment.domain.CommentStatus;
import illvent.backend.comment.dto.CommentRegisterRequestDTO;
import illvent.backend.comment.dto.CommentResponseDTO;
import illvent.backend.comment.dto.CommentUpdateRequestDTO;
import illvent.backend.member.domain.Member;
import illvent.backend.member.domain.MemberRepository;
import illvent.backend.post.domain.Post;
import illvent.backend.post.domain.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Optional<Comment> registerComment(Long postNo, CommentRegisterRequestDTO commentRegisterRequestDTO) {
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post does not exist"));

        Member member = memberRepository.findById(commentRegisterRequestDTO.getMemberNo()).orElseThrow(() ->
                new IllegalArgumentException("Member does not exist"));

        Comment comment = Comment.builder()
                .level(0L)
                .post(post)
                .member(member)
                .content(commentRegisterRequestDTO.getContent())
                .createDate(LocalDateTime.now())
                .status(CommentStatus.Y)
                .build();

        return Optional.of(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("Comment does not exist"));

        comment.updateStatus();
        commentRepository.save(comment);
    }

    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentUpdateRequestDTO commentUpdateRequestDTO) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("Comment does not exist"));

        comment.update(commentUpdateRequestDTO);

        commentRepository.save(comment);

        return new CommentResponseDTO(comment);
    }
}
