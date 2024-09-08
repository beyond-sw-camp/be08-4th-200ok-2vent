package event.backend.member.util;

import event.backend.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class SecurityUser extends User {
    private Member member;

    public SecurityUser(Member member) {
        super(member.getNo().toString(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
