package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;

import java.util.List;

public interface Behavior {

    Velocity calculateVelocity(List<Person> entities);

    boolean isReached();
}
