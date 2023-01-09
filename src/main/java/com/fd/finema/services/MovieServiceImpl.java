package com.fd.finema.services;

import com.fd.finema.bom.Movie;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.mapper.DtoToMovieMapper;
import com.fd.finema.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public void addMovie(MovieDTO movie)  {
        Optional<Movie> optional = movieRepository.findFirstByName(movie.getName());
        if(optional.isPresent()){
            throw new IllegalArgumentException("Movie is already present in database");
        }
        try {
            Movie mapMovie = movieMapper.mapMovie(movie);
            mapMovie.setActive(false);
            movieRepository.saveAndFlush(mapMovie);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MovieDTO> getListActiveMovies(){
        Optional<List<Movie>> allByReleaseDateAfter = movieRepository.getAllByReleaseDateAfter(LocalDate.now().minusDays(14));
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<MovieDTO> getUpcomingMovies(){
        Optional<List<Movie>> upcomingMovies = movieRepository.getFirst10ByReleaseDateAfterAndIsActiveOrderByReleaseDate(LocalDate.now(),false);
        return movieMapper.mapMovies(upcomingMovies.get());
    }
}
