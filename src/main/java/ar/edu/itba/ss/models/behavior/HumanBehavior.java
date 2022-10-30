package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.targets.PositionTarget;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class HumanBehavior implements Behavior {

    @Getter
    @Setter
    private PositionTarget target;

    @Override
    public void calculateTarget(Person me, List<Person> entities) {

    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return null;
    }

    @Override
    public void elude() {

    }

    @Override
    public boolean isReached(Person me) {
        return false;
    }
}
