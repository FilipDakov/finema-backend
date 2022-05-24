package com.fd.finema.services;

import com.fd.finema.bom.Movie;
import com.fd.finema.bom.Screening;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.mapper.ScreeningMapper;
import com.fd.finema.repository.MovieRepository;
import com.fd.finema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    @Autowired
    private ScreeningMapper screeningMapper;

    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createScreening(ScreeningDTO screeningDTO) {
        Screening screening = screeningMapper.mapScreeningFromDto(screeningDTO);
        Optional<List<Screening>> screenings = screeningRepository.getAllByDateAndHall(screeningDTO.getDate(), screening.getHall());
        if (screenings.isPresent()) {
            LocalTime dtoTime = LocalTime.parse(screeningDTO.getStartTime());
            for (Screening duplicateScreening : screenings.get()) {
                LocalTime ldmTime = LocalTime.parse(duplicateScreening.getStartTime());
                if (ldmTime.equals(dtoTime)) {
                    throw new IllegalArgumentException("There is screening for the chosen time!");
                } else if (ldmTime.isBefore(dtoTime)) {
                    ldmTime = ldmTime.plusMinutes(duplicateScreening.getMovie().getTimespan() + 15);
                    if (ldmTime.isAfter(dtoTime)) {
                        throw new IllegalArgumentException("Screening is overlapping with other screening! There is 15 min gap between movies!");
                    }
                } else {
                    dtoTime = dtoTime.plusMinutes(duplicateScreening.getMovie().getTimespan() + 15);
                    if (dtoTime.isAfter((ldmTime))) {
                        throw new IllegalArgumentException("Screening is overlapping with other screening! There is 15 min gap between movies!");
                    }
                }
            }
        }//end checking for time collision

        screeningRepository.save(screening);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void getCurrentWeekScreenings() {
        LocalDate now = LocalDate.now();

        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        Optional<List<Screening>> allByDateAfterAndDateBefore = screeningRepository.getAllByDateAfterAndDateBefore(convertToDateViaInstant(monday), convertToDateViaInstant(sunday));

        List<ScreeningDTO> screeningDTOS = screeningMapper.mapToDtoScreenings(allByDateAfterAndDateBefore.get());


        System.out.println();

    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
