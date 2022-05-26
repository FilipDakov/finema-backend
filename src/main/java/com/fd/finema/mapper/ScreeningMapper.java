package com.fd.finema.mapper;

import com.fd.finema.bom.*;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.repository.HallRepository;
import com.fd.finema.repository.MovieRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ScreeningMapper {

    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private MovieRepository movieRepository;

    public abstract Screening mapScreeningFromDto(ScreeningDTO dto);

    protected Hall mapHall(Integer hallNumber){
        Optional<Hall> hall = hallRepository.findByNumber(hallNumber);
        if(!hall.isPresent()){
            throw new IllegalArgumentException("Hall should not be null");
        }
        return hall.get();
    }

    protected Movie mapMovie(String name){
        Optional<Movie> movie = movieRepository.findFirstByName(name);
        if(!movie.isPresent()){
            throw new IllegalArgumentException("Movie with that name doesn't exists");
        }
        return movie.get();

    }


    @AfterMapping
    protected void afterMappingFromDto(ScreeningDTO dto, @MappingTarget Screening screening){
    //    String eeee = new SimpleDateFormat("EEEE").format(screening.getDate());
     //   Day day = Day.valueOf(new SimpleDateFormat("EEEE").format(screening.getDate()).toUpperCase(Locale.ROOT));
        Day day = Day.valueOf(dto.getDate().getDayOfWeek().name());
        screening.setDayOfWeek(day);
    }

    public abstract List<ScreeningDTO> mapToDtoScreenings(List<Screening> screenings);

    public abstract ScreeningDTO mapDtoFromLdmScreening(Screening screening);

    protected Integer mapHall(Hall hall) {
        if (hall != null) {
            return hall.getNumber();
        }

        return null;
    }

    protected String mapMovie(Movie movie) {
        if (movie != null) {
            return movie.getName();
        }
        return null;
    }

    @AfterMapping
    protected void afterMappingToDto(@MappingTarget ScreeningDTO dto, Screening screening){
        if(screening.getMovie()!= null){
            dto.setImgPath(screening.getMovie().getImgPath());
        }
    }

    protected String mapPremiereType(ScreeningPremiereType type){
        if(type == null){
            return null;
        }else{
            return type.getValue();
        }
    }

    protected String mapScreeningType(ScreeningTypeEnum type){
        if(type == null){
            return null;
        }else{
            return type.getValue();
        }
    }


    protected ScreeningTypeEnum mapStringToScreeningType(String input){
        if(input == null){return null;}
        else{
            return ScreeningTypeEnum.valueOf(input.toUpperCase(Locale.ROOT));
        }
    }

    protected ScreeningPremiereType mapStringToScreeningPremiereType(String input){
        if(input == null){return null;}
        else{
            return ScreeningPremiereType.valueOf(input.toUpperCase(Locale.ROOT));
        }
    }
}
