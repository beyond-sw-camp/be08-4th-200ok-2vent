package illvent.backend.wish.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {
    Wish findByMemberNoAndEventNo(Long memberId, Long eventId);
}
