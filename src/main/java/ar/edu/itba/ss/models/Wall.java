package ar.edu.itba.ss.models;

import static ar.edu.itba.ss.utils.Constants.VD_MAX;

public class Wall {

    private static final double RADIUS = 11;
    private static final Vector CENTER = new Vector(0, 0);

    public static boolean isColliding(Person person) {
        return person.getPosition().distanceTo(CENTER) >= RADIUS - person.getRadius();
    }

    // Bounce against the wall
    public static Velocity calculateVelocity(Person person) {
        final Vector direction = person.getPosition().directionTo(CENTER);
        return new Velocity(VD_MAX, direction.getAngle() + Math.PI / 4);
    }
}
