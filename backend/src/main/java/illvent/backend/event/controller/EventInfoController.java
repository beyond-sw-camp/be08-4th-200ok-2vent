//package illvent.backend.event.controller;
//
//import illvent.backend.event.domain.DateFilter;
//import illvent.backend.event.dto.EventInfoResponseDTO;
//import illvent.backend.event.dto.EventResponseDTO;
//import illvent.backend.event.service.EventInfoService;
//import io.swagger.v3.oas.annotations.Operation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RequestMapping("v1/api/events")
//@RestController
//public class EventInfoController {
//
//    private final EventInfoService eventInfoService;
//
//    @Operation(summary = "이벤트 리스트 전체 조회 API")
//    @GetMapping("/all")
//    public ResponseEntity<List<EventInfoResponseDTO>> getAllEvents() {
//
//        List<EventInfoResponseDTO> result = eventInfoService.getAllEvents();
//
//        return ResponseEntity.ok(result);
//    }
//
//    @Operation(summary = "이벤트 리스트 조건별 조회 API")
//    @GetMapping("")
//    public ResponseEntity<List<EventInfoResponseDTO>> getEventsByFilter(@RequestParam(value="date",required = false) DateFilter date,
//                                                                        @RequestParam(value="region",required = false) String region,
//                                                                        @RequestParam(value = "join",required = false)String join,
//                                                                        @RequestParam(value="price",required = false) String price,
//                                                                        @RequestParam(value="page",defaultValue ="0") int page,
//                                                                        @RequestParam(value = "size",defaultValue = "9") int size) {
////        System.out.println("date: " + date);
////        System.out.println("region: " + region);
////        System.out.println("join: " + join);
////        System.out.println("price: " + price);
//
//        if(region.equals("전체")){
//            region = null;
//        }
//        if(join.equals("onoff")){
//            join = null;
//        }
//        if(price.equals("freeAndPaid")){
//            price = null;
//        }
//
//        List<EventInfoResponseDTO> result = eventInfoService.getEventsByFilter(date,region,join,price,page,size);
//
//        return ResponseEntity.ok(result);
//    }
//
//
//
//
//}
