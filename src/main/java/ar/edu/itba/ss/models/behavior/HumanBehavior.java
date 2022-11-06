package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.models.*;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.models.Person.MAX_RADIUS;
import static ar.edu.itba.ss.models.Person.MIN_RADIUS;
import static java.lang.Math.min;

public class HumanBehavior extends PersonBehavior {

    public static final double V_WANDER = 0.3;
    public static final double VD_MAX = 3;

    public HumanBehavior(Person me) {
        super(me, V_WANDER, VD_MAX, VD_MAX);
    }

    @Override
    public Velocity calculateVelocity(List<Person> entities) {
        Velocity velocity;
        if ((velocity = calculateWallCollisionVelocity()) != null) {
            me.setNextRadius(MIN_RADIUS);
            return velocity;
        }
        if ((velocity = calculatePersonCollisionVelocity(entities.stream()
                .filter(p -> p.getState() == PersonState.HUMAN || p.getState() == PersonState.TRANSITIONING)
                .toList())) != null) {
            me.setNextRadius(MIN_RADIUS);
            return velocity;
        }

        me.setNextRadius(min(me.getRadius() + Simulator.DELTA_R, MAX_RADIUS));
        velocity = calculateEscapeVelocity(entities);
        return correctVelocity(velocity, entities);
    }

    private Velocity calculateEscapeVelocity(List<Person> entities) {
        final List<Person> zombies = entities.stream()
                .filter(p -> p.getState() == PersonState.ZOMBIE || p.getState() == PersonState.TRANSITIONING)
                .filter(p -> me.distanceTo(p) <= 4)
                .toList();
        if (zombies.isEmpty()) { // Wandering
            return calculateWanderVelocity();
        }
        final List<Vector> directions = new ArrayList<>();
        zombies.forEach(z -> {
            directions.add(me.getPosition().directionTo(z.getPosition()));
        });
        directions.forEach(v -> v.rotate(Math.PI));
        final Vector direction = Vector.sum(directions);
        final double speed = vdMax * Math.pow((me.getNextRadius() - MIN_RADIUS) / (MAX_RADIUS - MIN_RADIUS), Simulator.BETA);
        return new Velocity(speed, direction.getAngle());
    }

    @Override
    public boolean isReached() {
        return false;
    }

    @Override
    public void execute() {
        me.setRadius(me.getNextRadius());
    }
}
