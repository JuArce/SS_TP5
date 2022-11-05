package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Simulator;
import ar.edu.itba.ss.models.Velocity;

import java.util.List;

public class TransitioningBehavior extends PersonBehavior {

    public static final double DEFAULT_TRANSITION_TIME = 7;

    private double counter;

    private final double dt;

    public TransitioningBehavior(Person me) {
        super(me, 0, 0, 0);
        this.counter = 0;
        this.dt = Simulator.dt;
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

    @Override
    public void execute() {
        update();
        if (isReached()) {
            counter = 0;
            me.changeState(PersonState.ZOMBIE);
        }
    }
}
