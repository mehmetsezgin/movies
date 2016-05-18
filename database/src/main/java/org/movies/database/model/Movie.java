package org.movies.database.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by emehsez on 15.05.2016.
 */
@Entity
public class Movie {

    @Id
    @SequenceGenerator(name="movie_seq", sequenceName="MOVIE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="movie_seq")
    protected Long id;

    public Long getId() {
        return id;
    }

    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "Name is required, maximum 255 characters."
    )
    protected String title;

    protected Date releaseDate;

    protected String language;

    protected BigDecimal budget;

    protected BigDecimal boxOffice;

    @OneToMany(mappedBy = "movie")
    protected Set<MovieGenre> movieGenres = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BigDecimal boxOffice) {
        this.boxOffice = boxOffice;
    }

    public Set<MovieGenre> getMovieGenres() {
        return movieGenres;
    }
}
