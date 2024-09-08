package event.backend.post.service;

import event.backend.member.domain.Member;
import event.backend.member.domain.MemberRepository;
import event.backend.member.domain.MemberStatus;
import event.backend.post.domain.Post;
import event.backend.post.domain.PostRepository;
import event.backend.post.dto.PostPageResponseDTO;
import event.backend.post.dto.PostRegisterRequestDTO;
import event.backend.post.dto.PostResponseDTO;
import event.backend.post.dto.PostUpdateRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Optional<Post> registerPost(Long memberNo, PostRegisterRequestDTO postRegisterRequestDTO) {

        Member foundMember = memberRepository.findById(memberNo).orElseThrow(() ->
                new IllegalArgumentException("Member not found"));

        if(foundMember.getStatus() == MemberStatus.N) throw new IllegalArgumentException("Invalid member");

        Post post = Post.builder()
                .title(postRegisterRequestDTO.getTitle())
                .content(postRegisterRequestDTO.getContent())
                .region(postRegisterRequestDTO.getRegion())
                .member(foundMember)
                .createDate(LocalDateTime.now())
                .views(0)
                .build();

        return Optional.of(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long postNo){
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));

        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDTO updatePost(Long postNo, PostUpdateRequestDTO postUpdateRequestDTO) {
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));

        post.update(postUpdateRequestDTO);

        postRepository.save(post);

        return new PostResponseDTO(post);
    }

    public PostPageResponseDTO getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());

        Page<Post> posts = postRepository.findAll(pageable);

        return PostPageResponseDTO
                .builder()
                        .posts(posts.getContent().stream().map(PostResponseDTO::new).collect(Collectors.toList()))
                                .totalDataCount(posts.getTotalElements())
                                        .totalPageCount(posts.getTotalPages())
                                                .currentPageNumber(posts.getNumber())
                .build();

    }

    public PostResponseDTO getPost(Long postNo) {
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));
        post.updateViews();

        return new PostResponseDTO(postRepository.save(post));
    }

}
