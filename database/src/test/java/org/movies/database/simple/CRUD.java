package org.movies.database.simple;

import org.movies.database.environment.JPATest;
import org.movies.database.model.Actor;
import org.movies.database.model.Genre;
import org.movies.database.model.Movie;
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

}

