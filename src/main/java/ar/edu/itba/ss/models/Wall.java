package ar.edu.itba.ss.models;

import java.util.List;

public class Wall {

    private static final double RADIUS = 11;
    private static final Vector CENTER = new Vector(0, 0);

    public static boolean isColliding(Person person) {
        return person.getPosition().distanceTo(CENTER) >= RADIUS - person.getRadius();
    }

    // Bounce against the wall
    public static Velocity calculateVelocity(Person person) {
        final Vector direction = person.getPosition().directionTo(CENTER);
        return new Velocity(person.getBehavior().getVE(), direction.getAngle());
    }

    public static Vector getNearPoint(Person me) {
        if (me.getPosition().equals(CENTER)) {
            return new Vector(0, RADIUS);
        }
        final Vector tail = me.getPosition(); // A
        final Vector head = Vector.sum(List.of(me.getPosition(), me.getVelocity().toVector())); // B

        // Get vector equation
        final double m = (head.getY() - tail.getY()) / (head.getX() - tail.getX());
        final double b = tail.getY() - m * tail.getX();

        // Get intersection point with circle
        final double a = 1 + Math.pow(m, 2);
        final double b2 = 2 * (m * b - m * CENTER.getY() - CENTER.getX());
        final double c = Math.pow(CENTER.getX(), 2) + Math.pow(b, 2) - 2 * b * CENTER.getY() + Math.pow(CENTER.getY(), 2) - Math.pow(RADIUS, 2);
        final double x1 = (-b2 + Math.sqrt(Math.pow(b2, 2) - 4 * a * c)) / (2 * a);
        final double x2 = (-b2 - Math.sqrt(Math.pow(b2, 2) - 4 * a * c)) / (2 * a);

        final double y1 = m * x1 + b;
        final double y2 = m * x2 + b;

        // Return closest to head
        final Vector p1 = new Vector(x1, y1);
        final Vector p2 = new Vector(x2, y2);
        return p1.distanceTo(head) < p2.distanceTo(head) ? p1 : p2;
    }
}
