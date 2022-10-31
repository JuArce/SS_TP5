package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;
import lombok.Getter;

public class TimeTarget implements Target {

    @Getter
    private final double target;
    private double counter;

    public TimeTarget(double target) {
        this.target = target;
        this.counter = 0;
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return new Velocity(0, 0);
    }

    public void update(double dt) {
        counter += dt;
    }

    @Override
    public boolean isReached(Person me) {
        return target <= counter;
    }
}
