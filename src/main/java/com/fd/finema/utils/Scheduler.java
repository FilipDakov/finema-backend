package com.fd.finema.utils;

import com.fd.finema.bom.Movie;
import com.fd.finema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@EnableAsync
public class Scheduler {


    private MovieRepository movieRepository;

    public Scheduler(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Async
    @Scheduled(cron = "* 30 1 * * MON")
    public void scheduledCheckForActiveMovies(){
        Optional<List<Movie>> inactiveMovies = movieRepository.getAllByReleaseDateIsBetweenAndIsActive(LocalDate.now(), LocalDate.now().plusDays(7), false);
        if(inactiveMovies.isPresent()){
            List<Movie> movies = inactiveMovies.get();
            movies.forEach(x -> x.setActive(true));
            movieRepository.saveAll(movies);
        }

    }


    @Async
    @Scheduled(cron = "* 0 1 * * MON")
    public void scheduledCheckForInactiveMovies(){
        Optional<List<Movie>> inactiveMovies = movieRepository.getAllByReleaseDateBeforeAndIsActive(LocalDate.now().minusDays(28),true);
        if(inactiveMovies.isPresent()){
            List<Movie> movies = inactiveMovies.get();
            movies.forEach(x -> x.setActive(false));
            movieRepository.saveAll(movies);
        }

    }
}
