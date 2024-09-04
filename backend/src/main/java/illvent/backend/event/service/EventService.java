package illvent.backend.event.service;

import illvent.backend.event.domain.*;
import illvent.backend.event.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public Optional<Event> registerEvent(EventRegisterRequestDTO eventRegisterRequestDTO) {
        Event event = Event.builder()
                .title(eventRegisterRequestDTO.getTitle())
                .location(eventRegisterRequestDTO.getLocation())
                .address(eventRegisterRequestDTO.getAddress())
                .eventDate(eventRegisterRequestDTO.getEventDate())
                .imageUrl(eventRegisterRequestDTO.getImageUrl())
                .description(eventRegisterRequestDTO.getDescription())
                .region(eventRegisterRequestDTO.getRegion())
                .price(eventRegisterRequestDTO.getPrice())
                .views(0)
                .online(eventRegisterRequestDTO.isOnline())
                .offline(eventRegisterRequestDTO.isOffline())
                .x(eventRegisterRequestDTO.getX())
                .y(eventRegisterRequestDTO.getY())
                .build();

        return Optional.of(eventRepository.save(event));
    }

    @Transactional
    public EventResponseDTO updateEvent(Long eventNo, EventUpdateRequestDTO eventUpdateRequestDTO) {
        Event event = eventRepository.findById(eventNo).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));

        event = Event.builder()
                .no(eventNo)
                .title(eventUpdateRequestDTO.getTitle())
                .location(eventUpdateRequestDTO.getLocation())
                .address(eventUpdateRequestDTO.getAddress())
                .imageUrl(eventUpdateRequestDTO.getImageUrl())
                .description(eventUpdateRequestDTO.getDescription())
                .region(eventUpdateRequestDTO.getRegion())
                .price(eventUpdateRequestDTO.getPrice())
                .views(eventUpdateRequestDTO.getViews())
                .likes(eventUpdateRequestDTO.getLikes())
                .online(eventUpdateRequestDTO.isOnline())
                .offline(eventUpdateRequestDTO.isOffline())
                .x(eventUpdateRequestDTO.getX())
                .y(eventUpdateRequestDTO.getY())
                .build();

        eventRepository.save(event);

        return new EventResponseDTO(event);
    }

    @Transactional
    public void deleteEvent(Long eventNo) {
        Event event = eventRepository.findById(eventNo).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));

        eventRepository.delete(event);
    }

    @Transactional
    public EventResponseDTO getEvent(Long eventNo) {
        Event event = eventRepository.findById(eventNo).orElseThrow(() ->
                new IllegalArgumentException("Event not found"));
        event.updateViews();
        return new EventResponseDTO(eventRepository.save(event));
    }

    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(EventResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsOrderByViews() {
        return eventRepository.findTop10ByOrderByViewsDesc().stream()
                .map(EventResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsOrderByLikes(){
        return eventRepository.findTop10ByOrderByLikesDesc().stream()
                .map(EventResponseDTO::new)
                .collect(Collectors.toList());
    }

    public EventFilterPagingResponseDTO getEventsByFilter(Long loginUserId,DateFilter date, String region, String join, String price,int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> events = null;

        Boolean online = null;
        Boolean offline = null;

        LocalDate startDate = null;
        LocalDate endDate = null;

        // 날짜 계산
        if (date != null) {
            LocalDateRange dateRange = date.getDateRange();
            startDate = dateRange.getStartDate();
            endDate = dateRange.getEndDate();
        }


        // on off 계산
        if (join != null) {
            if (join.equals("online")) {
                online = true;
            } else if (join.equals("offline")) {
                offline = true;
            }
        }

        if (price != null) {
            if (price.equals("free")) {
                events = eventRepository.findEventInfoByConditionAndFree(loginUserId, pageable, startDate, endDate, online, offline, 0, region);
            } else if (price.equals("paid")) {
                events = eventRepository.findEventInfoByConditionAndPaid(loginUserId, pageable, startDate, endDate, online, offline, 0, region);
            }
        } else { // 전체 가격
            events = eventRepository.findEventInfoByConditionAndFree(loginUserId, pageable, startDate, endDate, online, offline, null, region);
        }


//        return events.stream().map(e -> new EventInfoResponseDTO(e.getNo(), e.getTitle(), e.getImageUrl(), e.getPrice(), e.getRegion(),
//                e.getEventDate(), e.isOnline(), e.isOffline(), e.getViews())).toList();


        List<EventInfoResponseDTO> data = events.stream().map(item -> {
            Event event = (Event) item[0];
            Boolean isWish = (Boolean) item[1];
            return EventInfoResponseDTO.builder()
                    .id(event.getNo())
                    .title(event.getTitle())
                    .imgUrl(event.getImageUrl())
                    .price(event.getPrice())
                    .region(event.getRegion())
                    .eventDate(event.getEventDate())
                    .online(event.isOnline())
                    .offline(event.isOffline())
                    .views(event.getViews())
                    .isWish(isWish)
                    .build();
        }).toList();

        return EventFilterPagingResponseDTO.builder()
                .totalPages(events.getTotalPages())
                .totalElements(events.getTotalElements())
                .pageNumber(events.getNumber())
                .pageSize(events.getSize())
                .contents(data)
                .build();
    }
}
