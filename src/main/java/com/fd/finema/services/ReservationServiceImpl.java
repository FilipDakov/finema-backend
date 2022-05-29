package com.fd.finema.services;

import com.fd.finema.bom.*;
import com.fd.finema.interfaces.LockDTO;
import com.fd.finema.interfaces.PersonDTO;
import com.fd.finema.interfaces.ReservationDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.mapper.ReservationMapper;
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
import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    ScreeningRepository screeningRepository;
    LockService lockService;
    ReservationRepository reservationRepository;
    LockRepository lockRepository;
    UserRepository userRepository;
    ReservationMapper reservationMapper;

    public ReservationServiceImpl(ScreeningRepository screeningRepository, LockService lockService
            , ReservationRepository reservationRepository, LockRepository lockRepository,
                                  UserRepository userRepository, ReservationMapper reservationMapper) {
        this.screeningRepository = screeningRepository;
        this.lockService = lockService;
        this.reservationRepository = reservationRepository;
        this.lockRepository = lockRepository;
        this.userRepository = userRepository;
        this.reservationMapper = reservationMapper;
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

        Screening screening = findScreening(reservationDto);
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
            Reservation reservation = buildReservation(reservationDto, screening, seat, user);
            reservationRepository.save(reservation);
            lockService.unlockSeat(new LockDTO(reservationDto.getScreening(), reservationDto.getUser(), seat));

        }


    }


    @Override
    @Transactional
    public List<ReservationDTO> getAllReservationByPerson(PersonDTO personDTO) {
        Optional<List<Reservation>> reservations = reservationRepository.findAllByFirstNameAndMiddleNameAndLastNameAndStatus(personDTO.getFirstName(),
                personDTO.getMiddleName(), personDTO.getLastName(), Status.NEW);

        if (reservations.isPresent()) {
            return  reservationMapper.mapReservationsToDTO(reservations.get());
        }

        return new ArrayList<>();
    }


    @Override
    @Transactional
    public void confirmReservation(ReservationDTO reservationDTO){

        Reservation reservation = getReservation(reservationDTO);

        reservation.setStatus(Status.CONFIRMED);
        reservationRepository.save(reservation);

    }


    @Override
    @Transactional
    public void deleteReservation(ReservationDTO reservationDTO){

        Reservation reservation = getReservation(reservationDTO);

        reservation.setStatus(Status.DELETED);
        reservationRepository.save(reservation);

    }

    @Override
    @Transactional
    public List<ReservationDTO> getReservationsForUser(String user){
        Optional<List<Reservation>> users = reservationRepository.findAllByUser_Email(user);
        return reservationMapper.mapReservationsToDTO(users.get());

    }

    private Reservation getReservation(ReservationDTO reservationDTO){

        Optional<Reservation> optionalReservation = reservationRepository.findFirstByScreeningAndSeatNumber(findScreening(reservationDTO), reservationDTO.getSeatNumbers().get(0));
        if(!optionalReservation.isPresent()){
            throw new IllegalArgumentException("NO reservation found");
        }

        Reservation reservation = optionalReservation.get();
        if(!reservation.getStatus().equals(Status.NEW)){
            throw new IllegalArgumentException("Reservation found is already deleted or confirmed");
        }
        return reservation;
    }


    private Screening findScreening(ReservationDTO reservationDTO){
        ScreeningDTO screeningDto = reservationDTO.getScreening();
        if (Objects.isNull(screeningDto)) {
            throw new IllegalArgumentException("Screening should not be null");
        }
        Optional<Screening> screeningOptional = screeningRepository.findFirstByDateAndStartTimeAndHall_NumberAndMovie_Name(screeningDto.getDate(), screeningDto.getStartTime(),
                screeningDto.getHall(), screeningDto.getMovie());

        if (!screeningOptional.isPresent()) {
            throw new IllegalArgumentException("No such screening found");
        }

        return screeningOptional.get();
    }

    private Reservation buildReservation(ReservationDTO reservationDto, Screening screening, Integer seat, User user) {
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
