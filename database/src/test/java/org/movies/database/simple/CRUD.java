package org.movies.database.simple;

import org.movies.database.environment.JPATest;
import org.movies.database.model.Actor;
import org.movies.database.model.Genre;
import org.movies.database.model.Movie;
import org.movies.database.model.MovieGenre;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.Calendar;

import static org.testng.Assert.assertEquals;

public class CRUD extends JPATest {

    @Override
    public void configurePersistenceUnit() throws Exception {
        configurePersistenceUnit("SimplePU");
    }

    @Test
    public void storeAndQueryItems() throws Exception {
        storeAndQueryItems("findItems");
    }

    public void storeAndQueryItems(String queryName) throws Exception {
        UserTransaction tx = TM.getUserTransaction();
        try {
            tx.begin();
            EntityManager em = JPA.createEntityManager();

            Genre action = new Genre();
            action.setName("Action");

            em.persist(action);

            Movie indochine = new Movie();
            indochine.setTitle("IndoChine");
            indochine.setLanguage("FR");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1992);
            indochine.setReleaseDate(calendar.getTime());

            em.persist(indochine);

            Actor cd = new Actor();
            cd.setName("Catherine Deneuve");
            calendar.set(Calendar.YEAR, 1943);
            cd.setDateOfBirth(calendar.getTime());
            cd.setGender("F");

            em.persist(cd);

            tx.commit();
            em.close();

            tx.begin();
            em = JPA.createEntityManager();

            Long genreId = action.getId();

            // Load in another persistence context
            Genre actionFromDb = em.find(Genre.class, genreId); // SQL SELECT

            // Initializes the Item proxy because we call getId(), which is
            // not mapped as an identifier property (the field is!)
            assertEquals(actionFromDb.getName(),action.getName()); // SQL SELECT

            tx.commit();
            em.close();
        } finally {
            TM.rollback();
        }
    }

    @Test
    public void testFilmAndGenre() throws Exception{
        UserTransaction tx = TM.getUserTransaction();
        try {
            tx.begin();
            EntityManager em = JPA.createEntityManager();

            Genre action = new Genre();
            action.setName("Action");

            Genre sciFi = new Genre();
            sciFi.setName("Science Fiction");

            em.persist(action);
            em.persist(sciFi);

            Movie indochine = new Movie();
            indochine.setTitle("IndoChine");
            indochine.setLanguage("FR");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1992);
            indochine.setReleaseDate(calendar.getTime());

            em.persist(indochine);

            Movie dieHard = new Movie();
            dieHard.setTitle("Die Hard");
            dieHard.setLanguage("EN");
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1995);
            dieHard.setReleaseDate(calendar.getTime());

            em.persist(dieHard);

            Movie terminator = new Movie();
            terminator.setTitle("Terminator");
            terminator.setLanguage("EN");
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1990);
            terminator.setReleaseDate(calendar.getTime());

            em.persist(terminator);

            Movie alien = new Movie();
            alien.setTitle("Alien");
            alien.setLanguage("EN");
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1991);
            alien.setReleaseDate(calendar.getTime());

            em.persist(alien);

            MovieGenre link1 = new MovieGenre(action,indochine);
            MovieGenre link2 = new MovieGenre(action,dieHard);
            MovieGenre link3 = new MovieGenre(sciFi,terminator);
            MovieGenre link4 = new MovieGenre(sciFi,alien);
            MovieGenre link5 = new MovieGenre(action,alien);

            em.persist(link1);
            em.persist(link2);
            em.persist(link3);
            em.persist(link4);
            em.persist(link5);

            tx.commit();
            em.close();

            long actionId = action.getId();
            long sciFiId = sciFi.getId();

            long indoChineId = indochine.getId();
            long dieHardId = dieHard.getId();
            long terminatorId = terminator.getId();
            long alienId = alien.getId();

            tx.begin();
            em = JPA.createEntityManager();

            // Load in another persistence context
            //Genre actionFromDb = em.find(Genre.class, actionId); // SQL SELECT
            //Genre sciFiFromDb = em.find(Genre.class, sciFiId); // SQL SELECT

            Movie alienFromDb = em.find(Movie.class,alienId);

            // Initializes the Item proxy because we call getId(), which is
            // not mapped as an identifier property (the field is!)
            //assertEquals(actionFromDb.getMovieGenres().size(),3); // SQL SELECT
            //assertEquals(sciFiFromDb.getMovieGenres().size(),2); // SQL SELECT

            assertEquals(alienFromDb.getMovieGenres().size(),2); // SQL SELECT

            tx.commit();
            em.close();
        } finally {
            TM.rollback();
        }
    }

}

