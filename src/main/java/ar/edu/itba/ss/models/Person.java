package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Person implements Movable {

    @Getter
    private final Vector position;

    @Getter
    private final Velocity velocity;

    @Getter
    @Setter
    private PersonState state;
    @Setter
    @Getter
    private Behavior behavior;

    @Getter
    @Setter
    private double radius;

    public final static double MAX_RADIUS = 0.32;
    public final static double MIN_RADIUS = 0.15;

    public Person(Vector position, Velocity velocity, PersonState state, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.state = state;
        try {
            this.behavior = state.getBehaviorClass().getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.radius = radius;
    }

    @Override
    public void move(double dt) {
        final Velocity v = behavior.calculateVelocity(this);
        this.velocity.setModule(v.getModule());
        this.velocity.setAngle(v.getAngle());

        this.position.setX(position.getX() + velocity.getVelocityX() * dt);
        this.position.setY(position.getY() + velocity.getVelocityY() * dt);

    }

    public double distanceTo(Person person) {
        return position.distanceTo(person.getPosition());
    }

    public double angleTo(Person person) {
        return position.angleTo(person.getPosition());
    }

    public void calculateTarget(List<Person> people) {
        behavior.calculateTarget(this, people);
    }
}
