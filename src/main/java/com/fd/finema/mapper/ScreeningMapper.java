package com.fd.finema.mapper;

import com.fd.finema.bom.Hall;
import com.fd.finema.bom.Movie;
import com.fd.finema.bom.Screening;
import com.fd.finema.interfaces.ScreeningDTO;
import com.fd.finema.repository.HallRepository;
import com.fd.finema.repository.MovieRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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
}
