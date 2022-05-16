package com.fd.finema.repository;

import com.fd.finema.bom.Genre;
import com.fd.finema.bom.GenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre,Long> {
    Optional<Genre> findGenreByType(GenreEnum genreEnum);
}
