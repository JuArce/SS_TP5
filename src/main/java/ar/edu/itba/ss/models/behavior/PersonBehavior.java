package ar.edu.itba.ss.models.behavior;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Vector;
import ar.edu.itba.ss.models.Velocity;
import ar.edu.itba.ss.models.Wall;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.utils.Random.getRandom;

public abstract class PersonBehavior implements Behavior {

    public static double A = -0.1; // CMP correction
    public static double B = 4; // CMP correction

    protected final Person me;
    @Getter
    protected final double vWander;
    @Getter
    protected final double vdMax;
    @Getter
    protected final double vE;

    public PersonBehavior(Person me, double vWander, double vdMax, double vE) {
        this.me = me;
        this.vWander = vWander;
        this.vdMax = vdMax;
        this.vE = vE;
    }

    protected Velocity calculateWallCollisionVelocity() {
        if (Wall.isColliding(me)) {
            return Wall.calculateVelocity(me);
        }
        return null;
    }

    protected Velocity calculatePersonCollisionVelocity(List<Person> entities) {
        final List<Vector> escapeVectors = new ArrayList<>();
        for (Person person : entities) {
            if (person == me) {
                continue;
            }
            if (me.distanceTo(person) < me.getRadius() + person.getRadius()) {
                escapeVectors.add(me.getPosition().directionTo(person.getPosition()).rotate(Math.PI));
            }
        }
        if (!escapeVectors.isEmpty()) {
            return new Velocity(vdMax, Vector.sum(escapeVectors).getAngle());
        }
        return null;
    }

    protected Velocity correctVelocity(Velocity v, List<Person> entities) {
        Velocity vCorrected = v;
        List<Vector> possibleCollisions = entities.stream()
                .filter(p -> p != me)
                .filter(p -> me.distanceTo(p) + me.getRadius() + p.getRadius() < Person.DOV)
                .map(Person::getPosition)
                .filter(position -> v.toVector().angleTo(me.getPosition().directionTo(position)) <= Person.FOV / 2).toList();

        List<Vector> corrections = new ArrayList<>();
        possibleCollisions.forEach(p -> {
            Vector direction = me.getPosition().directionTo(p);
            double angle = v.toVector().angleTo(direction);
            double distance = me.getPosition().distanceTo(p);
            double xCorrection = direction.getX() * A * Math.exp(-distance / B) * Math.cos(angle);
            double yCorrection = direction.getY() * A * Math.exp(-distance / B) * Math.cos(angle);
            corrections.add(new Vector(xCorrection, yCorrection));
        });

        if (!corrections.isEmpty()) {
            corrections.add(v.toVector().normalize());
            Vector correction = Vector.sum(corrections);
            vCorrected = new Velocity(v.getModule(), correction.getAngle());
        }

        /**
         * Avoid wall
         */
        Vector nearWallPoint = Wall.getNearPoint(me);
        if (me.getPosition().distanceTo(nearWallPoint) < Person.DOV && v.toVector().angleTo(me.getPosition().directionTo(nearWallPoint)) <= Math.PI / 2) {
            vCorrected.setAngle(v.getAngle() + getWallCorrection(vCorrected, nearWallPoint));
        }

        return vCorrected;
    }

    public Velocity calculateWanderVelocity() {
        return new Velocity(vWander, me.getVelocity().getAngle() + getRandom(- Math.PI / 36, Math.PI / 36));
    }

    public double getWallCorrection(Velocity v, Vector nearWallPoint) {
        return + Math.PI / (2 * me.getPosition().distanceTo(nearWallPoint));

//        final double angle = v.getAngle();
//        if ((angle > 0 && angle < Math.PI / 2)
//                || (angle < 0 && angle > -Math.PI / 2)) { // Primer y cuarto cuadrante ??? resto un ??ngulo
//            return + Math.PI / (2 * me.getPosition().distanceTo(nearWallPoint));
//        } else {
//            return + Math.PI / (2 * me.getPosition().distanceTo(nearWallPoint));
//        }
    }

}
