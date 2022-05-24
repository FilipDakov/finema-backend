package com.fd.finema.services;

import com.fd.finema.bom.Movie;
import com.fd.finema.interfaces.MovieDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MovieService {

    void addMovie(MovieDTO movie) throws Exception;

    List<MovieDTO> getListActiveMovies();

    @Transactional(propagation = Propagation.REQUIRED)
    List<MovieDTO> getCurrentMovies();
}
