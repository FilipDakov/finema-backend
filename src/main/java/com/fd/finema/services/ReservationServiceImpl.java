package com.fd.finema.services;

import com.fd.finema.bom.*;
import com.fd.finema.interfaces.LockDTO;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.repository.LockRepository;
import com.fd.finema.repository.ReservationRepository;
import com.fd.finema.repository.ScreeningRepository;
import com.fd.finema.security.User;
import com.fd.finema.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    ScreeningRepository screeningRepository;
    LockService lockService;
    ReservationRepository reservationRepository;
    LockRepository lockRepository;
    UserRepository userRepository;

    public ReservationServiceImpl(ScreeningRepository screeningRepository, LockService lockService
            , ReservationRepository reservationRepository, LockRepository lockRepository, UserRepository userRepository) {
        this.screeningRepository = screeningRepository;
        this.lockService = lockService;
        this.reservationRepository = reservationRepository;
        this.lockRepository = lockRepository;
        this.userRepository =userRepository;
    }

    @Override
    public Seat[] getFreeSeats(ScreeningDTO screeningDto) {
        Optional<Screening> screening = screeningRepository.findFirstByDateAndStartTimeAndHall_NumberAndMovie_Name(screeningDto.getDate(), screeningDto.getStartTime(),
                screeningDto.getHall(), screeningDto.getMovie());

        if (!screening.isPresent()) {
            throw new IllegalArgumentException("Screening is not found");
        }

        Seat[] freeSeats = new Seat[screening.get().getHall().getAvailableSeats()];
        Arrays.fill(freeSeats, Seat.FREE);
        lockService.unlockExpiredSeats();
        Optional<List<Reservation>> reservations = reservationRepository.findAllByScreeningAndStatusIsNot(screening.get(), Status.DELETED);
        if (reservations.isPresent()) {
            reservations.get().forEach(reservation -> {
                freeSeats[reservation.getSeatNumber() - 1] = Seat.TAKEN;
            });
        }

        Optional<List<SeatLocker>> lockedSeats = lockRepository.findAllById_Screening(screening.get());
        if (lockedSeats.isPresent()) {
            lockedSeats.get().forEach(lock -> {
                freeSeats[lock.getId().getSeatNumber() - 1] = Seat.LOCKED;
            });
        }

        return freeSeats;
    }


    @Override
    @Transactional
    public void createReservation(ReservationDTO reservationDto) {
        User user = userRepository.findByEmail(reservationDto.getUser());
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("No such user found");
        }

        ScreeningDTO screeningDto = reservationDto.getScreening();
        if (Objects.isNull(screeningDto)) {
            throw new IllegalArgumentException("Screening should not be null");
        }
        Optional<Screening> screeningOptional = screeningRepository.findFirstByDateAndStartTimeAndHall_NumberAndMovie_Name(screeningDto.getDate(), screeningDto.getStartTime(),
                screeningDto.getHall(), screeningDto.getMovie());

        if (!screeningOptional.isPresent()) {
            throw new IllegalArgumentException("No such screening found");
        }


        Screening screening = screeningOptional.get();
        LocalDate today = LocalDate.of(
                LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth());
        if (screening.getDate().isBefore(today)) {
            throw new IllegalArgumentException("You cannot make reservation for past dates");
        } else if (screening.getDate().equals(today) &&
                LocalTime.parse(screening.getStartTime()).isBefore(LocalTime.now())) {
            throw new IllegalArgumentException("You cannot make reservation for past screenings");
        }


        for (Integer seat : reservationDto.getSeatNumbers()) {
            Optional<Reservation> reservationOptional = reservationRepository.findFirstByScreeningAndSeatNumber(screening, seat);
            if (reservationOptional.isPresent() && !reservationOptional.get().getStatus().equals(Status.DELETED)) {
                throw new IllegalArgumentException("Reservation already exists");
            }
            Reservation reservation = buildReservation(reservationDto,screening,seat,user);
            reservationRepository.save(reservation);
            lockService.unlockSeat(new LockDTO(reservationDto.getScreening(),reservationDto.getUser(),seat));

        }


    }


    private Reservation buildReservation(ReservationDTO reservationDto,Screening screening,Integer seat,User user){
        Reservation reservation = new Reservation();
        reservation.setFirstName(reservationDto.getFirstName());
        reservation.setMiddleName(reservationDto.getMiddleName());
        reservation.setLastName(reservationDto.getMiddleName());
        reservation.setScreening(screening);
        reservation.setUser(user);
        reservation.setSeatNumber(seat);
        reservation.setStatus(Status.NEW);
        return reservation;
    }
}
