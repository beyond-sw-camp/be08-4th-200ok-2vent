package event.backend.member.controller;

import event.backend.event.dto.EventResponseDTO;
import event.backend.member.domain.Member;
import event.backend.member.dto.MemberRegisterRequestDTO;
import event.backend.member.dto.MemberLoginRequestDTO;
import event.backend.member.dto.MemberResponseDTO;
import event.backend.member.dto.MemberUpdateRequestDTO;
import event.backend.member.service.MemberService;
import event.backend.post.dto.PostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "회원 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 정보를 등록하는 API")
    @PostMapping("/register")
    public ResponseEntity<String> registerMember(@RequestBody @Valid MemberRegisterRequestDTO memberRegisterRequestDTO) {
        memberService.registerMember(memberRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody @Valid MemberLoginRequestDTO memberLoginRequestDTO) {
        Member member = memberService.findMemberByEmail(memberLoginRequestDTO.getEmail());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, memberService.createToken(memberLoginRequestDTO).toString())
                .body(new MemberResponseDTO(member));
    }

    @Operation(summary = "회원 정보를 수정하는 API")
    @PutMapping("/update")
    public ResponseEntity<String> updateMember(@RequestBody @Valid MemberUpdateRequestDTO memberUpdateRequestDTO) {
        memberService.updateMember(memberUpdateRequestDTO);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 상태를 탈퇴 상태로 수정하는 API")
    @PutMapping("/delete/{email}")
    public ResponseEntity<String> deleteMember(@PathVariable String email) {
        memberService.deleteMember(email);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원의 모든 관심 행사를 반환하는 API")
    @GetMapping("/list/wish/{memberNo}")
    public ResponseEntity<List<EventResponseDTO>> getAllWishEvents(@PathVariable Long memberNo){
        List<EventResponseDTO> events = memberService.getAllWishEvents(memberNo);

        return ResponseEntity.ok(events);
    }

    @Operation(summary = "회원이 작성한 글을 반환하는 API")
    @GetMapping("/list/post/{memberNo}")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(@PathVariable Long memberNo){
        List<PostResponseDTO> posts = memberService.getAllPosts(memberNo);

        return ResponseEntity.ok(posts);
    }
}
