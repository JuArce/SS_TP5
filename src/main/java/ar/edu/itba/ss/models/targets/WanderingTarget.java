package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;

import static ar.edu.itba.ss.utils.Random.getRandom;

public class WanderingTarget implements Target {

    private static final double WANDERING_SPEED = 0.3;
    private int wanderingTime = 0;
    private int wanderingTimeLimit = 15;

    @Override
    public Velocity calculateVelocity(Person me) {
        final double angle;
        double delta = ++wanderingTime == wanderingTimeLimit ? getRandom(- Math.PI / 4, Math.PI / 4) : 0;
        angle = me.getVelocity().getAngle() + delta;
        wanderingTime %= wanderingTimeLimit;
        return new Velocity(WANDERING_SPEED, angle);
    }

    @Override
    public boolean isReached(Person me) {
        return false;
    }
}
