package com.fd.finema.mapper;

import com.fd.finema.bom.Actor;
import com.fd.finema.bom.Genre;
import com.fd.finema.bom.GenreEnum;
import com.fd.finema.bom.Movie;
import com.fd.finema.interfaces.ActorDTO;
import com.fd.finema.interfaces.MovieDTO;
import com.fd.finema.repository.ActorRepository;
import com.fd.finema.repository.GenreRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class DtoToMovieMapper {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ActorRepository actorRepository;

    public List<MovieDTO> mapMovies(List<Movie> movies){
        if(movies == null || movies.isEmpty()){
            return null;
        }
        List<MovieDTO> listMovieDTOS = new ArrayList<>();
        for(Movie movie : movies){
            listMovieDTOS.add(mapToDto(movie));
        }
        return listMovieDTOS;
    }

    public abstract MovieDTO mapToDto(Movie movie);

    public abstract Movie mapMovie(MovieDTO dto);

    protected Actor mapActor(ActorDTO dto) {
        if (dto == null) {
            return null;
        }
        Optional<Actor> optionalActor = actorRepository.findFirstByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());
        return optionalActor.orElseGet(() -> new Actor(dto.getFirstName(), dto.getLastName()));
    }

    protected List<String> mapGenreDto(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return null;
        }
        return genres.stream().map(x -> x.getType().getValue()).collect(Collectors.toList());
    }

    protected List<Genre> mapListGenre(List<String> genres) {
        if (genres == null) {
            return null;
        }

        List<Genre> movieGenre = new ArrayList<>();
        for (String genre : genres) {
            Optional<Genre> genreOptional = genreRepository.findGenreByType(GenreEnum.valueOf(genre));
            if (genreOptional.isPresent()) {
                movieGenre.add(genreOptional.get());
            } else {
                movieGenre.add(new Genre(genre));
            }
        }
        return movieGenre;
    }
}
