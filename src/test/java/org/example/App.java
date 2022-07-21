package org.example;

import org.example.manyToMany.model.Actor;
import org.example.manyToMany.model.Movie;
import org.example.oneToMany.model.Item;
import org.example.oneToMany.model.Person;
import org.example.oneToOne.model.OneToOnePerson;
import org.example.oneToOne.model.Passport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class)
                .addAnnotatedClass(OneToOnePerson.class).addAnnotatedClass(Passport.class).addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            /** ONE TO MANY*/
            Person person = new Person("Test person", 55);
            Item newItem = new Item("something");
            person.addItem(newItem);
            session.save(person);

            /** ONE TO ONE*/
            OneToOnePerson oneToOnePerson = new OneToOnePerson("Test person", 50);
            Passport passport = new Passport(oneToOnePerson, 123456);
            oneToOnePerson.setPassport(passport);
            session.save(oneToOnePerson);

            /** MANY TO MANY*/
            //add 2 actors and 1 film and join it
            Movie movie = new Movie("Some name", 2000);
            Actor actor1 = new Actor("Actor 1", 30);
            Actor actor2 = new Actor("Actor 2", 50);
            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));
            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            session.save(movie);
            session.save(actor1);
            session.save(actor2);

            //add a new movie
            Movie movie2 = new Movie("some name 2", 1995);
            Actor actor = session.get(Actor.class, 1);
            movie2.setActors(new ArrayList<>(Collections.singletonList(actor)));
            actor.getMovies().add(movie2);
            session.save(movie2);
            session.getTransaction().commit();

            // remove movie to the actor
            Actor actorClean = session.get(Actor.class, 2);
            Movie movieToRemove = actorClean.getMovies().get(0);
            actorClean.getMovies().remove(0);
            movieToRemove.getActors().remove(actorClean);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
