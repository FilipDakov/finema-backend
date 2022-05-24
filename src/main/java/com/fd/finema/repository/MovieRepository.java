package com.fd.finema.repository;

import com.fd.finema.bom.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<List<Movie>> getAllByReleaseDateAfter(Date date);
    Optional<List<Movie>> getAllByIsActive(Boolean active);
    Optional<Movie> findFirstByName(String name);

}
