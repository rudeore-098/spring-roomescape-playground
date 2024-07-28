package roomescape.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain.Reservation;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    private AtomicLong index = new AtomicLong(1);
    private List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/reservation")
    public String reservation(){
        return "reservation";
    }
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> creatReservation(@RequestBody Reservation reservation) {
        Reservation addReservation = reservation.toEntity(index.getAndIncrement(),reservation);
        reservations.add(addReservation);
        return ResponseEntity.created(URI.create("/reservations/" + addReservation.getId())).build();
    }
}
