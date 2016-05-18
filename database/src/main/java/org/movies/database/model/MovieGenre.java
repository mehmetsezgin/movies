package org.movies.database.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by emehsez on 18.05.2016.
 */
@Entity
@Table(name = "MOVIE_GENRE")
@org.hibernate.annotations.Immutable
public class MovieGenre {

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "GENRE_ID")
        protected Long genreId;

        @Column(name = "MOVIE_ID")
        protected Long movieId;

        public Id() {
        }

        public Id(Long genreId, Long movieId) {
            this.genreId = genreId;
            this.movieId = movieId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof Id) {
                Id that = (Id) o;
                return this.genreId.equals(that.genreId)
                        && this.movieId.equals(that.movieId);
            }
            return false;
        }

        public int hashCode() {
            return genreId.hashCode() + movieId.hashCode();
        }
    }

    @EmbeddedId
    protected Id id = new Id();

    @ManyToOne
    @JoinColumn(
            name = "GENRE_ID",
            insertable = false, updatable = false)
    protected Genre genre;

    @ManyToOne
    @JoinColumn(
            name = "MOVIE_ID",
            insertable = false, updatable = false)
    protected Movie movie;


    public MovieGenre() {
    }

    public MovieGenre(Genre genre, Movie movie) {

        // Set fields
        this.genre = genre;
        this.movie = movie;

        // Set identifier values
        this.id.genreId = genre.getId();
        this.id.movieId = movie.getId();

        // Guarantee referential integrity if made bidirectional
        genre.getMovieGenres().add(this);
        movie.getMovieGenres().add(this);
    }

    public Id getId() {
        return id;
    }

    public Genre getGenre() {
        return genre;
    }

    public Movie getMovie() {
        return movie;
    }


}

