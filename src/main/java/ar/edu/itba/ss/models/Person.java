package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

public class Person implements Movable {

    @Getter
    @Setter
    private Vector position;

    @Getter
    private Velocity velocity;

    @Setter
    @Getter
    private Behavior behavior;

    @Getter
    @Setter
    private double radius;

    private final static double MAX_RADIUS = 0.32;

    private final static double MIN_RADIUS = 0.15;

    public Person(Vector position, Velocity velocity, Behavior behavior, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.behavior = behavior;
        this.radius = radius;
    }

    @Override
    public void move(double dt) {

        position.setX(position.getX() + velocity.getVelocityX() * dt);
        position.setY(position.getY() + velocity.getVelocityY() * dt);

    }
}
