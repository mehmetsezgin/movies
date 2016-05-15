package org.movies.database.model;

import javax.persistence.*;

/**
 * Created by emehsez on 15.05.2016.
 */
@Entity
public class Genre {

    @Id
    @SequenceGenerator(name="movie_seq", sequenceName="MOVIE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="movie_seq")
    protected Long id;

    public Long getId(){
        return id;
    }


    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
