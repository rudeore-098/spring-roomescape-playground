package roomescape.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class Reservation {

    private Long id;
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Date cannot be null")
    @NotEmpty(message = "Date cannot be empty")
    @NotBlank(message = "Date cannot be blank")
    private String date;
    @NotNull(message = "Time cannot be null")
    @NotEmpty(message = "Time cannot be empty")
    @NotBlank(message = "Time cannot be blank")
    private String time;


    public Reservation(){

    }

    public Reservation(Long id, String name, String date, String time){
        this.id= id;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public Reservation(String name, String date, String time){
        this.name = name;
        this.date = date;
        this.time = time;
    }
    public static Reservation toEntity(Long id, Reservation reservation){
        return new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }



}
