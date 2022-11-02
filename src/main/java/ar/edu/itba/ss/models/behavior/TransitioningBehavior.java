package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;
import lombok.Setter;

import java.util.List;

public class TransitioningBehavior implements Behavior {

    public static final double DEFAULT_TRANSITION_TIME = 7;

    private double counter;

    @Setter
    private double dt;

    public TransitioningBehavior() {
        this.counter = 0;
    }

    @Override
    public Velocity calculateVelocity(List<Person> entities) {
        return new Velocity(0, 0);
    }

    @Override
    public boolean isReached() {
        return counter >= DEFAULT_TRANSITION_TIME;
    }

    public void update() {
        counter += dt;
    }
}
