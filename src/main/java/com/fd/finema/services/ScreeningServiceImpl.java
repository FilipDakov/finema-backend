package com.fd.finema.services;

import com.fd.finema.bom.Day;
import com.fd.finema.bom.Screening;
import com.fd.finema.interfaces.MovieScreeningDTO;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.mapper.ScreeningMapper;
import com.fd.finema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningMapper screeningMapper;

    private final ScreeningRepository screeningRepository;

    public ScreeningServiceImpl(ScreeningMapper screeningMapper, ScreeningRepository screeningRepository) {
        this.screeningMapper = screeningMapper;
        this.screeningRepository = screeningRepository;
    }

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
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
//    public List<ScreeningDTO> getCurrentWeekScreenings() {
//        Optional<List<Screening>> allByDateAfterAndDateBefore = getCurrentScreenings();
//
//        return screeningMapper.mapToDtoScreenings(allByDateAfterAndDateBefore.get());
//    }

    private Optional<List<Screening>> getCurrentScreenings() {
        LocalDate now = LocalDate.now();

        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return screeningRepository.getAllByDateAfterAndDateBefore(monday.minusDays(1), sunday.plusDays(1));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MovieScreeningDTO> getCurrentScreeningsForDay(String day) {


        Optional<List<Screening>> currentScreenings = getCurrentScreenings();
        if (currentScreenings.isPresent()) {
            List<Screening> screenings = currentScreenings.get().stream()
                    .filter(screening -> screening.getDayOfWeek().equals(Day.valueOf(day.toUpperCase(Locale.ROOT))))
                    .collect(Collectors.toList());

            Map<String, List<ScreeningDTO>> map = new HashMap<>();

            List<ScreeningDTO> screeningDTOS = screeningMapper.mapToDtoScreenings(screenings);

            screeningDTOS.forEach(scr -> {
                if(map.containsKey(scr.getMovie())){
                    map.get(scr.getMovie()).add(scr);
                }else{
                    map.put(scr.getMovie(), new ArrayList<>(Arrays.asList(scr)));
                }
            });

            return map.entrySet().stream()
                    .map(x -> new MovieScreeningDTO(x.getKey(), x.getValue()))
                    .collect(Collectors.toList());

           // return map;
        }
        return null;
    }

    @Override
    public String getTodayDay() {
       return LocalDate.now().getDayOfWeek().toString();
    }

}
