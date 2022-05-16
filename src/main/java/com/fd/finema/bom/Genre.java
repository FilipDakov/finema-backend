package com.fd.finema.bom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Genre extends BaseEntity {
    @Column(unique = true)
    private GenreEnum type;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;

    public Genre(GenreEnum type) {
        this.type = type;
    }

    public Genre(String type){
        this.type = GenreEnum.valueOf(type);
    }

    public Genre() {
    }

    public GenreEnum getType() {
        return type;
    }

    public void setType(GenreEnum type) {
        this.type = type;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
