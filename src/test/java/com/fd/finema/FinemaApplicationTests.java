package com.fd.finema;

import com.fd.finema.bom.AgeRestriction;
import com.fd.finema.interfaces.ActorDTO;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.services.MovieService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

@Transactional
@SpringBootTest
public class FinemaApplicationTests {

    @Autowired
    MovieService movieService;


    private MovieDTO testMovie;

    @BeforeEach
    private void before(){
        testMovie = new MovieDTO();
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setFirstName("test");
        actorDTO.setLastName("testov");
        testMovie.setActors(Arrays.asList(actorDTO));
        testMovie.setAgeRestriction(AgeRestriction.R);
        testMovie.setGenres(Arrays.asList("COMEDY","ACTION"));
        testMovie.setDescription("test description");
        testMovie.setImgPath("test");
        testMovie.setReleaseDate(LocalDate.now());
        testMovie.setTimespan(150);
        testMovie.setName("Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplicateMovieTest() throws Exception {
 //       MovieDTO movieDTO = new MovieDTO();
        movieService.addMovie(testMovie);
    }

}
