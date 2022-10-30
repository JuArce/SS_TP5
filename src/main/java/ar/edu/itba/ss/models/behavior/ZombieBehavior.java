package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.targets.HumanTarget;
import lombok.Getter;

import java.util.List;

import static ar.edu.itba.ss.utils.Random.getRandom;

public class ZombieBehavior implements Behavior {

    private static final double DOV = 4;
    private static final double V_ACTIVE = 4;
    private static final double V_INACTIVE = 0.3;
    @Getter
    private HumanTarget target;
    private int wanderingTime = 0;
    private int wanderingTimeLimit = 15;


    @Override
    public void calculateTarget(Person me, List<Person> entities) {
        this.target = entities.stream()
                .filter(person -> person.getState() == PersonState.HUMAN)
                .min((person1, person2) -> {
                    double distance1 = me.distanceTo(person1);
                    double distance2 = me.distanceTo(person2);
                    return Double.compare(distance1, distance2);
                })
                .filter(person -> me.distanceTo(person) < DOV)
                .map(HumanTarget::new)
                .orElse(null);
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        if (target == null) {
            final double angle;
            if (wanderingTime++ == wanderingTimeLimit) {
                angle = me.getVelocity().getAngle() + getRandom(- Math.PI / 4, Math.PI / 4);
                wanderingTime = 0;
            } else {
                angle = me.getVelocity().getAngle();
            }

            return new Velocity(V_INACTIVE, angle);
        }
        return new Velocity(V_ACTIVE, me.angleTo(target.getTarget()));
    }

    @Override
    public void elude() {

    }

    @Override
    public boolean isReached(Person me) {
        return target != null && me.distanceTo(target.getTarget()) - me.getRadius() - target.getTarget().getRadius() < 0;
    }
}
