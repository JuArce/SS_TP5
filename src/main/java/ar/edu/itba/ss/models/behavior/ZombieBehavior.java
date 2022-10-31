package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.targets.HumanTarget;
import ar.edu.itba.ss.models.targets.WanderingTarget;
import lombok.Getter;

import java.util.List;

public class ZombieBehavior implements Behavior {

    private static final double DOV = 4;

    @Getter
    private Target target;

    @Override
    public void calculateTarget(Person me, List<Person> entities) {
        final Target t = entities.stream()
                .filter(person -> person.getState() == PersonState.HUMAN)
                .min((person1, person2) -> {
                    double distance1 = me.distanceTo(person1);
                    double distance2 = me.distanceTo(person2);
                    return Double.compare(distance1, distance2);
                })
                .filter(person -> me.distanceTo(person) < DOV)
                .map(HumanTarget::new)
                .orElse(null);

        if (t != null) {
            target = t;
        } else if (target == null) {
            target = new WanderingTarget();
        }
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return target.calculateVelocity(me);
    }

    @Override
    public void elude() {

    }

    @Override
    public boolean isReached(Person me) {
        return target.isReached(me);
    }
}
