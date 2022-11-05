package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.models.*;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.utils.Constants.*;
import static java.lang.Math.min;

public class HumanBehavior extends PersonBehavior {

    public HumanBehavior(Person me) {
        super(me);
    }

    @Override
    public Velocity calculateVelocity(List<Person> entities) {
        Velocity velocity;
        if ((velocity = calculateWallCollisionVelocity()) != null) {
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
        velocity = correctVelocity(velocity, entities);
        return velocity;
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
        final double speed = VD_MAX * Math.pow((me.getRadius() - MIN_RADIUS) / (MAX_RADIUS - MIN_RADIUS), BETA);
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
