package illvent.backend.member.service;

import illvent.backend.event.domain.Event;
import illvent.backend.event.dto.EventResponseDTO;
import illvent.backend.member.domain.*;
import illvent.backend.member.dto.MemberLoginRequestDTO;
import illvent.backend.member.dto.MemberRegisterRequestDTO;
import illvent.backend.member.dto.MemberUpdateRequestDTO;
import illvent.backend.member.util.JwtTokenProvider;
import illvent.backend.post.domain.Post;
import illvent.backend.post.dto.PostResponseDTO;
import illvent.backend.wish.domain.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Transactional
    public Optional<Member> registerMember(MemberRegisterRequestDTO memberRegisterRequestDTO){

        String hashPassword = passwordEncoder.encode(memberRegisterRequestDTO.getPassword());

        Member member = Member.builder()
                .email(memberRegisterRequestDTO.getEmail())
                .password(hashPassword)
                .name(memberRegisterRequestDTO.getName())
//                .nickname(memberRegisterRequestDTO.getNickname())
                .location(memberRegisterRequestDTO.getLocation())
                .status(MemberStatus.Y)
                .role(MemberRole.USER)
                .build();

        return Optional.of(memberRepository.save(member));
    }

    @Transactional
    public Member findMemberByEmailPassword(MemberLoginRequestDTO memberLoginRequestDTO) {

        Member member = memberRepository.findByEmail(memberLoginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(memberLoginRequestDTO.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 맞지 않습니다.");
        }
        if (member.getStatus().equals(MemberStatus.N)){
            throw  new IllegalArgumentException("탈퇴한 회원입니다.");
        }
        return member;
    }

    @Transactional
    public Member findMemberByEmail(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("Member not found"));

        return member;
    }

    @Transactional
    public ResponseCookie createToken(MemberLoginRequestDTO memberLoginRequestDTO) {

        Member member = this.findMemberByEmailPassword(memberLoginRequestDTO);

        String accessToken = jwtTokenProvider.createAccessToken(accessHeader ,member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(refreshHeader, member.getEmail());

        RefreshToken newRefreshToken = RefreshToken.builder()
                .username(member.getEmail())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(new Date(new Date().getTime() + jwtTokenProvider.getRefreshTokenValidTime()).getTime())
                .build();

        refreshTokenRepository.save(newRefreshToken);

        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(3600)
                .build();

        RefreshToken found = refreshTokenRepository.findByAccessToken(newRefreshToken.getAccessToken()).orElseThrow(() ->
                new IllegalArgumentException("invalid email"));

        return cookie;
    }

    @Transactional
    public Optional<Void> updateMember(MemberUpdateRequestDTO memberUpdateRequestDTO) {

        Member member = memberRepository.findByEmail(memberUpdateRequestDTO.getEmail()).orElseThrow(() ->
                        new IllegalArgumentException("Invalid email address: " + memberUpdateRequestDTO.getEmail()));

        member.update(memberUpdateRequestDTO);

        return Optional.empty();
    }

    @Transactional
    public Optional<Void> deleteMember(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("Invalid email address: " + email));

        member.updateStatus();

        return Optional.empty();
    }

    public List<EventResponseDTO> getAllWishEvents(Long memberNo){
        Member member = memberRepository.findById(memberNo).orElseThrow(() ->
                new IllegalArgumentException("Member not found"));

        List<Wish> wishes = member.getWishes();
        List<Event> events = new ArrayList<>();
        for(Wish wish : wishes) events.add(wish.getEvent());

        return events.stream()
                .map(EventResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getAllPosts(Long memberNo){
        Member member = memberRepository.findById(memberNo).orElseThrow(() ->
                new IllegalArgumentException("Member not found"));
        List<Post> posts = member.getPosts();

        return posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }
}
