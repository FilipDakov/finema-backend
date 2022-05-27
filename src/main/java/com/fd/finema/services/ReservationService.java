package com.fd.finema.services;

import com.fd.finema.bom.Seat;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationService {

    Seat[] getFreeSeats(ScreeningDTO screeningDto);

    @Transactional
    void createReservation(ReservationDTO reservationDto);
}
