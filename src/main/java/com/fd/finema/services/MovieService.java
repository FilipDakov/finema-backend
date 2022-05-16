package com.fd.finema.services;

import com.fd.finema.bom.Movie;
import com.fd.finema.interfaces.MovieDTO;

import java.util.List;

public interface MovieService {

    void addMovie(MovieDTO movie) throws Exception;

    List<MovieDTO> getListActiveMovies();
}
