package roomescape.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }

    //예약 조회
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }

     // 예약 추가
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> creatReservation(@RequestBody Reservation reservation) {
        Reservation addReservation = reservation.toEntity(index.getAndIncrement(),reservation);
        reservations.add(addReservation);
        return ResponseEntity.created(URI.create("/reservations/" + addReservation.getId())).build();
    }


    // 예약 취소
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        Reservation deleteReservation = reservations.stream()
                .filter(it->Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(deleteReservation);

        return ResponseEntity.noContent().build();
    }
}
