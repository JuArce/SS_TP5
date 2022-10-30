package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.targets.TimeTarget;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TransitioningBehavior implements Behavior {

    public static final double DEFAULT_TRANSITION_TIME = 7;

    @Getter
    private TimeTarget target;

    @Setter
    private double dt;


    public TransitioningBehavior(TimeTarget target) {
        this.target = target;
    }

    public TransitioningBehavior(){
        this.target = new TimeTarget(DEFAULT_TRANSITION_TIME);
    }

    @Override
    public void calculateTarget(Person me, List<Person> entities) {
        target.update(this.dt);
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return new Velocity(0,0);
    }

    @Override
    public void elude() {

    }

    @Override
    public boolean isReached(Person me) {
        return target.isReached();
    }
}
