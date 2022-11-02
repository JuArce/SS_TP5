package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Vector;
import ar.edu.itba.ss.models.Velocity;

import java.util.ArrayList;
import java.util.List;

public class HumanBehavior implements Behavior {

    private final Person me;

    public HumanBehavior(Person me) {
        this.me = me;
    }

    @Override
    public Velocity calculateVelocity(List<Person> entities) {
        final List<Person> zombies = entities.stream().filter(p -> p.getState() == PersonState.ZOMBIE).toList();
        final List<Vector> directions = new ArrayList<>();
        zombies.forEach(z -> {
            directions.add(me.getPosition().directionTo(z.getPosition()));
        });
        directions.forEach(v -> v.rotate(Math.PI));
        final Vector direction = Vector.sum(directions).normalize();
        return new Velocity(me.getVelocity().getModule(), direction.getAngle());
    }

    @Override
    public boolean isReached() {
        return false;
    }
}
