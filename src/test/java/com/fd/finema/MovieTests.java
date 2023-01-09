package com.fd.finema;


import com.fd.finema.bom.AgeRestriction;
import com.fd.finema.bom.Movie;
import com.fd.finema.interfaces.ActorDTO;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.repository.MovieRepository;
import com.fd.finema.services.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest()
@Transactional()
public class MovieTests {


    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    private MovieDTO testMovie;

    @BeforeEach
    private void before() {
        testMovie = new MovieDTO();
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setFirstName("test");
        actorDTO.setLastName("testov");
        testMovie.setActors(Arrays.asList(actorDTO));
        testMovie.setAgeRestriction(AgeRestriction.R);
        testMovie.setGenres(Arrays.asList("COMEDY", "ACTION"));
        testMovie.setDescription("test description");
        testMovie.setImgPath("test");
        testMovie.setReleaseDate(LocalDate.now());
        testMovie.setTimespan(150);
        testMovie.setName("Test");
    }


    @Test()
    public void successfullyAddedMovieTest() throws Exception {
        movieService.addMovie(testMovie);
        Optional<Movie> optionalMovie = movieRepository.findFirstByName(testMovie.getName());
        Assertions.assertTrue(optionalMovie.isPresent());
    }

    @Test()
    public void duplicateMovieTest() throws Exception {
        movieService.addMovie(testMovie);
        Assertions.assertThrows(IllegalArgumentException.class, () -> movieService.addMovie(testMovie));
    }

}
