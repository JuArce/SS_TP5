package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;
import lombok.Getter;
import lombok.Setter;

public class HumanTarget implements Target {

    private static final double ACTIVE_SPEED = 4;

    @Getter
    @Setter
    private Person target;

    public HumanTarget(Person target) {
        this.target = target;
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return new Velocity(ACTIVE_SPEED, me.angleTo(target));
    }

    @Override
    public boolean isReached(Person me) {
        return me.distanceTo(target) <= me.getRadius() + target.getRadius();
    }
}
