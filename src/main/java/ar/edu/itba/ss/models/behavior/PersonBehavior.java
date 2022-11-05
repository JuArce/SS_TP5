package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Vector;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.Wall;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.utils.Constants.*;
import static ar.edu.itba.ss.utils.Random.getRandom;

public abstract class PersonBehavior implements Behavior {

    protected final Person me;

    public PersonBehavior(Person me) {
        this.me = me;
    }

    protected Velocity calculateWallCollisionVelocity() {
        if (Wall.isColliding(me)) {
            return Wall.calculateVelocity(me);
        }
        return null;
    }

    protected Velocity calculatePersonCollisionVelocity(List<Person> entities) {
        final List<Vector> escapeVectors = new ArrayList<>();
        for (Person person : entities) {
            if (person == me) {
                continue;
            }
            if (me.distanceTo(person) < me.getRadius() + person.getRadius()) {
                escapeVectors.add(me.getPosition().directionTo(person.getPosition()).rotate(Math.PI));
            }
        }
        if (!escapeVectors.isEmpty()) {
            return new Velocity(VD_MAX, Vector.sum(escapeVectors).getAngle());
        }
        return null;
    }

    protected Velocity correctVelocity(Velocity v, List<Person> entities) {
        List<Person> possibleCollisions = entities.stream()
                .filter(p -> p != me)
                .filter(p -> me.distanceTo(p) + me.getRadius() + p.getRadius() < DOV)
                .filter(p -> v.toVector().angleTo(me.getPosition().directionTo(p.getPosition())) <= FOV / 2)
                .toList();

        List<Vector> corrections = new ArrayList<>();
        possibleCollisions.forEach(p -> {
            Vector direction = me.getPosition().directionTo(p.getPosition());
            double angle = v.toVector().angleTo(direction);
            double distance = me.distanceTo(p);
            double xCorrection = direction.getX() * A * Math.exp(-distance / B) * Math.cos(angle);
            double yCorrection = direction.getY() * A * Math.exp(-distance / B) * Math.cos(angle);
            corrections.add(new Vector(xCorrection, yCorrection));
        });
        if (!corrections.isEmpty()) {
            corrections.add(v.toVector());
            Vector correction = Vector.sum(corrections);
            return new Velocity(v.getModule(), correction.getAngle());
        }
        return v;
    }

    public Velocity calculateWanderVelocity() {
        return new Velocity(V_WANDER, me.getVelocity().getAngle() + getRandom(- Math.PI / 6, Math.PI / 6));
    }


}