package roomescape.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.exception.NotFoundReservationException;

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
    // 예외처리
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> creatReservation(@RequestBody Reservation reservation) {
        Reservation addReservation = reservation.toEntity(index.getAndIncrement(),reservation);
        if(reservation.getDate().equals(null) || reservation.getDate().equals(""))
            throw new NotFoundReservationException("Invalid reservation information");
        if(reservation.getName().equals(null) || reservation.getDate().equals(""))
            throw new NotFoundReservationException("Invalid reservation information");
        if(reservation.getTime().equals(null) || reservation.getDate().equals(""))
            throw new NotFoundReservationException("Invalid reservation information");

        reservations.add(addReservation);
        return ResponseEntity.created(URI.create("/reservations/" + addReservation.getId())).build();
    }


    // 예약 취소
    // 예외처리
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        Reservation deleteReservation = reservations.stream()
                .filter(it->Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundReservationException("Reservation not found with id: " + id));

        reservations.remove(deleteReservation);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
