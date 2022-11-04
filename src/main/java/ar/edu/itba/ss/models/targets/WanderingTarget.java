package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.Wall;

import static ar.edu.itba.ss.utils.Constants.V_WANDER;
import static ar.edu.itba.ss.utils.Random.getRandom;

public class WanderingTarget implements Target {

    private int wanderingTime = 0;
    private final int wanderingTimeLimit = 15;

    @Override
    public Velocity calculateVelocity(Person me) {
        if (Wall.isColliding(me)) {
            Velocity velocity = Wall.calculateVelocity(me);
            velocity.setModule(V_WANDER);
            return velocity;
        }
        final double angle;
        double delta = ++wanderingTime == wanderingTimeLimit ? getRandom(- Math.PI / 4, Math.PI / 4) : 0;
        angle = me.getVelocity().getAngle() + delta;
        wanderingTime %= wanderingTimeLimit;
        return new Velocity(V_WANDER, angle);
    }

    @Override
    public boolean isReached(Person me) {
        return false;
    }

    @Override
    public void execute(Person me) {
        // Nothing to do
    }
}
