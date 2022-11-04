package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.*;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.utils.Constants.*;
import static java.lang.Math.min;

public class HumanBehavior implements Behavior {

    private final Person me;

    public HumanBehavior(Person me) {
        this.me = me;
    }

    @Override
    public Velocity calculateVelocity(List<Person> entities) {
        final List<Vector> escapeVectors = new ArrayList<>();
        if (Wall.isColliding(me)) {
//            escapeVectors.add(Wall.calculateVelocity(me).toVector());
            return new Velocity(VD_MAX, Wall.calculateVelocity(me).toVector().getAngle());
        }
        for (Person person : entities) {
            if (person == me) {
                continue;
            }
            if (me.distanceTo(person) <= me.getRadius() + person.getRadius()) {
                Vector direction = me.getPosition().directionTo(person.getPosition()).rotate(Math.PI);
//                Vector escapeVector = Vector.sum(List.of(direction, me.getPosition()));
                escapeVectors.add(direction);
            }
        }
        if (!escapeVectors.isEmpty()) {
            me.setRadius(MIN_RADIUS);
            final Vector escapeVector = Vector.sum(escapeVectors);
            return new Velocity(VD_MAX, escapeVector.getAngle());
        }
        me.setRadius(min(me.getRadius() + Simulator.DELTA_R, MAX_RADIUS));

        final List<Person> zombies = entities.stream()
                .filter(p -> p.getState() == PersonState.ZOMBIE || p.getState() == PersonState.TRANSITIONING)
                .filter(p -> me.distanceTo(p) <= 4)
                .toList();
        if (zombies.isEmpty()) {
            return new Velocity(0, 0);
        }
        final List<Vector> directions = new ArrayList<>();
        zombies.forEach(z -> {
            directions.add(me.getPosition().directionTo(z.getPosition()));
        });
        directions.forEach(v -> v.rotate(Math.PI));
        final Vector direction = Vector.sum(directions);
        final double speed = VD_MAX * Math.pow((me.getRadius() - MIN_RADIUS)/(MAX_RADIUS - MIN_RADIUS), BETA);
        return new Velocity(speed, direction.getAngle());
    }

    @Override
    public boolean isReached() {
        return false;
    }

    @Override
    public void execute() {
        //nothing to do
    }
}
