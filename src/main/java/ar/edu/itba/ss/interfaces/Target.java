package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;

public interface Target {

    Velocity calculateVelocity(Person me);

    boolean isReached(Person me);

    void execute(Person me);

}
