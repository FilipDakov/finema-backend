package com.fd.finema.services;

import com.fd.finema.bom.Movie;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.mapper.DtoToMovieMapper;
import com.fd.finema.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final DtoToMovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, DtoToMovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addMovie(MovieDTO movie) throws Exception {
        Optional<Movie> optional = movieRepository.findFirstByName(movie.getName());
        if(optional.isPresent()){
            throw new IllegalArgumentException("Movie is already present in database");
        }
        try {
            Movie mapMovie = movieMapper.mapMovie(movie);
            movieRepository.saveAndFlush(mapMovie);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MovieDTO> getListActiveMovies(){
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-14);
        Optional<List<Movie>> allByReleaseDateAfter = movieRepository.getAllByReleaseDateAfter(calendar.getTime());
        return movieMapper.mapMovies(allByReleaseDateAfter.get());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MovieDTO> getCurrentMovies(){
        Optional<List<Movie>> activeMovies = movieRepository.getAllByIsActive(true);
        if(!activeMovies.isPresent()){
            throw new IllegalArgumentException("No active movies found");
        }
        return movieMapper.mapMovies(activeMovies.get());
    }
}
