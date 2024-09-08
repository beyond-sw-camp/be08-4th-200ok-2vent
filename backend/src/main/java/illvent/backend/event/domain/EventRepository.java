package illvent.backend.event.domain;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findTop10ByOrderByViewsDesc();

    List<Event> findTop10ByOrderByLikesDesc();

    @Query(value = "select e, "
            +"case when (w.member.no = :userId) then true else false end as isWish "
            +"from Event e "
            +"left join Wish w on e.no=w.event.no and w.member.no = :userId "
            +"where (:startDate is null or e.eventDate>=:startDate) and "
            + "(:endDate is null or e.eventDate<=:endDate) and "
            + "(:online is null or e.online is true) and "
            + "(:offline is null or e.offline is true) and "
            + "(:region is null or e.region=:region) and "
            + "(:price is null or e.price=0)",
            countQuery = "select e from Event e where "
                    + "(:startDate is null or e.eventDate>=:startDate) and "
                    + "(:endDate is null or e.eventDate<=:endDate) and "
                    + "(:online is null or e.online is true) and "
                    + "(:offline is null or e.offline is true) and "
                    + "(:region is null or e.region=:region) and "
                    + "(:price is null or e.price=0)")
    Page<Object[]> findEventInfoByConditionAndFree(
            @Param("userId") Long userId,
            Pageable pageable,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("online") Boolean online,
            @Param("offline") Boolean offline,
            @Param("price") Integer price,
            @Param("region") String region

    );


    @Query(value = "select e, "
            +"case when (w.member.no = :userId) then true else false end as isWish "
            +"from Event e "
            +"left join Wish w on e.no=w.event.no and w.member.no = :userId "
            +"where (:startDate is null or e.eventDate>=:startDate) and "
            + "(:endDate is null or e.eventDate<=:endDate) and "
            + "(:online is null or e.online is true) and "
            + "(:offline is null or e.offline is true) and "
            + "(:region is null or e.region=:region) and "
            + "(:price is null or e.price!=0)",
            countQuery = "select e from Event e where "
                    + "(:startDate is null or e.eventDate>=:startDate) and "
                    + "(:endDate is null or e.eventDate<=:endDate) and "
                    + "(:online is null or e.online is true) and "
                    + "(:offline is null or e.offline is true) and "
                    + "(:region is null or e.region=:region) and "
                    + "(:price is null or e.price!=0)")  // 가격 유료
    Page<Object[]> findEventInfoByConditionAndPaid(
            @Param("userId") Long userId,
            Pageable pageable,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("online") Boolean online,
            @Param("offline") Boolean offline,
            @Param("price") Integer price,
            @Param("region") String region
    );

}