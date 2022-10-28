package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

public class Person implements Movable {

    @Getter
    @Setter
    private Vector position;

    @Getter
    private Velocity velocity;

    @Override
    public void move(double dt) {

        position.setX(position.getX() + velocity.getVelocityX() * dt);
        position.setY(position.getY() + velocity.getVelocityY() * dt);

    }
}
