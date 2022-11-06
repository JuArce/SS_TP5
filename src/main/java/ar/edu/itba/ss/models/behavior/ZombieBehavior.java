package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Simulator;
import ar.edu.itba.ss.models.Velocity;

import java.util.List;

import static ar.edu.itba.ss.models.Person.*;
import static java.lang.Math.min;

public class ZombieBehavior extends PersonBehavior {

    public static final double V_WANDER = 0.3;
    public static final double VD_MAX = 3;

    private Person target;

    public ZombieBehavior(Person me) {
        super(me, V_WANDER, VD_MAX, VD_MAX);
        target = null;
    }

    private Velocity calculateTargetVelocity(List< Person> entities) {
        this.target = entities.stream()
                .filter(person -> person.getState() == PersonState.HUMAN)
                .min((person1, person2) -> {
                    double distance1 = me.distanceTo(person1);
                    double distance2 = me.distanceTo(person2);
                    return Double.compare(distance1, distance2);
                })
                .filter(person -> me.distanceTo(person) < DOV).orElse(null);

        if (target != null) {
            return new Velocity(VD_MAX, me.getPosition().directionTo(target.getPosition()).getAngle());
        }
        return calculateWanderVelocity();
    }

    @Override
    public Velocity calculateVelocity(List<Person> entities) {
        Velocity velocity;
        if ((velocity = calculateWallCollisionVelocity()) != null) {
            return velocity;
        }
        if ((velocity = calculatePersonCollisionVelocity(entities.stream()
                .filter(p -> p.getState() == PersonState.ZOMBIE || p.getState() == PersonState.TRANSITIONING)
                .toList())) != null) {
            me.setNextRadius(MIN_RADIUS);
            return velocity;
        }
        me.setNextRadius(min(me.getRadius() + Simulator.DELTA_R, MAX_RADIUS));

        return calculateTargetVelocity(entities);

//        return correctVelocity(velocity, entities);
    }

    @Override
    public boolean isReached() {
        if (target == null) {
            return false;
        }
        return me.distanceTo(target) <= me.getRadius() + target.getRadius();
    }

    @Override
    public void execute() {
        if (isReached()) {
            if (target.getState() == PersonState.HUMAN) {
                target.changeState(PersonState.TRANSITIONING);
            }
            if (me.getState() == PersonState.ZOMBIE) {
                me.changeState(PersonState.TRANSITIONING);
            }
        }
        me.setRadius(me.getNextRadius());

    }
}
