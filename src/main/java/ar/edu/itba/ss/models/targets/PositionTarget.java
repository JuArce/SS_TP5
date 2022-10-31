package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Vector;
import ar.edu.itba.ss.models.Velocity;
import lombok.Getter;
import lombok.Setter;


public class PositionTarget implements Target {

    @Getter
    @Setter
    private Vector target;

    public PositionTarget(Vector target) {
        this.target = target;
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return null;
    }

    @Override
    public boolean isReached(Person me) {
        return me.getPosition().distanceTo(target) <= me.getRadius();
    }
}
