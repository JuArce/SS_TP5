package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Velocity;

import java.util.List;

import static ar.edu.itba.ss.utils.Constants.DT;

public class TransitioningBehavior implements Behavior {

    public static final double DEFAULT_TRANSITION_TIME = 7;

    private double counter;


    private double dt;

    private Person me;

    public TransitioningBehavior(Person me) {
        this.counter = 0;
        this.me = me;
        this.dt = DT;
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
