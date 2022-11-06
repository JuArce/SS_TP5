package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.*;
import ar.edu.itba.ss.models.behavior.HumanBehavior;
import ar.edu.itba.ss.models.behavior.ZombieBehavior;
import ar.edu.itba.ss.utils.CsvExporter;
import ar.edu.itba.ss.utils.PositionReader;
import ar.edu.itba.ss.utils.VelocityExporter;

import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.itba.ss.models.Person.MAX_RADIUS;
import static ar.edu.itba.ss.utils.Random.getRandom;

public class MultiNhApp {
    public static void main(String[] args) {
        List<Integer> humansQty = List.of(2, 10, 40, 80, 140, 200, 260, 320);

        humansQty.stream().parallel().forEach(hQty -> {
            final List<Vector> positions = PositionReader.readPositions(String.format("src/main/resources/input/positions_%s.txt", hQty));
            final List<Person> entities = positions.stream().map(p -> new Person(p, new Velocity(HumanBehavior.V_WANDER, getRandom(0, 2 * Math.PI)), PersonState.HUMAN, MAX_RADIUS)).collect(Collectors.toList());
            entities.add(new Person(new Vector(0, 0), new Velocity(HumanBehavior.V_WANDER, getRandom(0, 2 * Math.PI)), PersonState.ZOMBIE,  MAX_RADIUS));

            final double dt = Person.MIN_RADIUS / (2 * ZombieBehavior.VD_MAX);
            final double beta = 0.9;
            final double tau = 0.5;

            System.out.println("Simulation with " + hQty + " humans");
            System.out.println("dt: " + dt);

            Exporter csvExporter = new CsvExporter("output/ovito/", String.format("output_%s.csv", hQty));
            csvExporter.open();
            Exporter velocityExporter = new VelocityExporter("output/velocity/", String.format("velocity_%s.csv", hQty));
            velocityExporter.open();

            final Simulator system = new Simulator(entities, dt, beta, tau, csvExporter, velocityExporter);
            system.simulate();

            csvExporter.close();
            velocityExporter.close();
            System.out.println("Simulation with " + hQty + " humans finished");

        });
    }
}
