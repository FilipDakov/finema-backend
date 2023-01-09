package com.fd.finema.services;

import com.fd.finema.bom.Seat;
import com.fd.finema.interfaces.PersonDTO;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReservationService {

    Seat[] getFreeSeats(ScreeningDTO screeningDto);

    @Transactional
    void createReservation(ReservationDTO reservationDto);

    @Transactional
    List<ReservationDTO> getAllReservationByPerson(PersonDTO reservationDto);

    @Transactional
    List<ReservationDTO> getAllReservationByPerson( String firstName,String middleName,
                                                    String lastName);

    @Transactional
    void confirmReservation(ReservationDTO reservationDTO);

    @Transactional
    void deleteReservation(ReservationDTO reservationDTO);

    @Transactional
    List<ReservationDTO> getReservationsForUser(String user);

    List<ReservationDTO> getAllReservations();
}
