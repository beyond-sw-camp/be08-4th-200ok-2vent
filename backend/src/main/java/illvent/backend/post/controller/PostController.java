package illvent.backend.post.controller;

import illvent.backend.post.dto.PostPageResponseDTO;
import illvent.backend.post.dto.PostRegisterRequestDTO;
import illvent.backend.post.dto.PostResponseDTO;
import illvent.backend.post.dto.PostUpdateRequestDTO;
import illvent.backend.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시판 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/post")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시물을 등록하는 API")
    @PostMapping("/register/{memberNo}")
    public ResponseEntity<String> registerPost(@PathVariable Long memberNo ,@RequestBody PostRegisterRequestDTO postRegisterRequestDTO) {
        postService.registerPost(memberNo, postRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "게시물을 삭제하는 API")
    @DeleteMapping("/delete/{postNo}")
    public ResponseEntity<String> deletePost(@PathVariable Long postNo){
        postService.deletePost(postNo);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "모든 게시물을 반환하는 API")
    @GetMapping("/list")
    public ResponseEntity<PostPageResponseDTO> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size){
        PostPageResponseDTO allPosts = postService.getAllPosts(page, size);

        return ResponseEntity.ok(allPosts);
    }

    @Operation(summary = "특정 게시물을 반환하는 API")
    @GetMapping("/{postNo}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable Long postNo){
        PostResponseDTO post = postService.getPost(postNo);

        return ResponseEntity.ok(post);
    }

    @Operation(summary = "특정 게시물을 수정하는 API")
    @PutMapping("/update/{postNo}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postNo,
                                                           @RequestBody PostUpdateRequestDTO postUpdateRequestDTO){
        PostResponseDTO post = postService.updatePost(postNo, postUpdateRequestDTO);

        return ResponseEntity.ok(post);
    }

}
