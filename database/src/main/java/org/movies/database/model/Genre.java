package org.movies.database.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by emehsez on 15.05.2016.
 */
@Entity
public class Genre {

    @Id
    @SequenceGenerator(name="movie_seq", sequenceName="MOVIE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="movie_seq")
    protected Long id;

    protected String name;

    @OneToMany(mappedBy = "genre")
    protected Set<MovieGenre> movieGenres = new HashSet<>();

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MovieGenre> getMovieGenres() {
        return movieGenres;
    }
}
