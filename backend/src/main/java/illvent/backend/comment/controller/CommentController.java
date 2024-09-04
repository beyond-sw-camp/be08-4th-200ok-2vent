package illvent.backend.comment.controller;

import illvent.backend.comment.dto.CommentRegisterRequestDTO;
import illvent.backend.comment.dto.CommentResponseDTO;
import illvent.backend.comment.dto.CommentUpdateRequestDTO;
import illvent.backend.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글을 등록하는 API")
    @PostMapping("/register/{postNo}")
    public ResponseEntity<String> registerComment(@PathVariable Long postNo,
                                                  @RequestBody CommentRegisterRequestDTO commentRegisterRequestDTO) {
        commentService.registerComment(postNo, commentRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "댓글을 삭제하는 API")
    @PutMapping("/delete/{commentNo}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentNo) {

        commentService.deleteComment(commentNo);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글을 수정하는 API")
    @PutMapping("/update/{commentNo}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long commentNo,
                                                            @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO) {
        CommentResponseDTO comment = commentService.updateComment(commentNo, commentUpdateRequestDTO);

        return ResponseEntity.ok(comment);
    }

}
