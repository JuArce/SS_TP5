package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static ar.edu.itba.ss.utils.Constants.MAX_RADIUS;

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
    @Getter
    @Setter
    private double nextRadius;

    public Person(Vector position, Velocity velocity, PersonState state, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.state = state;
        try {
            this.behavior = state.getBehaviorClass().getConstructor(Person.class).newInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.radius = radius;
    }


    public void changeState(PersonState state) {
        this.state = state;
        this.radius = MAX_RADIUS;
        try {
            this.behavior = state.getBehaviorClass().getConstructor(Person.class).newInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void calculateVelocity(List<Person> entities) {
        final Velocity v = behavior.calculateVelocity(entities);
        if (v != null) {
            this.velocity.setModule(v.getModule());
            this.velocity.setAngle(v.getAngle());
        }
    }

    @Override
    public void move(double dt) {
        this.position.setX(position.getX() + velocity.getVelocityX() * dt);
        this.position.setY(position.getY() + velocity.getVelocityY() * dt);
    }

    public double distanceTo(Person person) {
        return position.distanceTo(person.getPosition());
    }

    public double angleTo(Person person) {
        return position.angleTo(person.getPosition());
    }

    public void execute(){
        behavior.execute();
    }


}
