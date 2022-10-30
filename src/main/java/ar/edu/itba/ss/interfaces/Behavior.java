package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;

import java.util.List;

public interface Behavior {

    void calculateTarget(Person me, List<Person> entities);

    Velocity calculateVelocity(Person me);

    void elude();

    boolean isReached(Person me);
}
