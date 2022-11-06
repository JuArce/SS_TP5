package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.*;
import ar.edu.itba.ss.models.behavior.HumanBehavior;
import ar.edu.itba.ss.models.behavior.ZombieBehavior;
import ar.edu.itba.ss.utils.CsvExporter;
import ar.edu.itba.ss.utils.PositionReader;
import ar.edu.itba.ss.utils.VelocityExporter;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ar.edu.itba.ss.models.Person.MAX_RADIUS;
import static ar.edu.itba.ss.utils.Random.getRandom;

public class MultiVzApp {

    public static void main(String[] args) {
        final List<Double> vzList = List.of(1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);
        final int tries = 5;
        final int hQty = 140;

        vzList.forEach(vz -> {
            IntStream.range(0, tries).forEach(i -> {
                final List<Vector> positions = PositionReader.readPositions(String.format("src/main/resources/input/positions_%s.txt", hQty));
                final List<Person> entities = positions.stream().map(p -> new Person(p, new Velocity(HumanBehavior.V_WANDER, getRandom(0, 2 * Math.PI)), PersonState.HUMAN, MAX_RADIUS)).collect(Collectors.toList());
                entities.add(new Person(new Vector(0, 0), new Velocity(HumanBehavior.V_WANDER, getRandom(0, 2 * Math.PI)), PersonState.ZOMBIE, MAX_RADIUS));

                ZombieBehavior.VD_MAX = vz;

                final double dt = Person.MIN_RADIUS / (2 * Math.max(HumanBehavior.VD_MAX, ZombieBehavior.VD_MAX));
                final double beta = 0.9;
                final double tau = 0.5;

                System.out.println("Simulation " + i + " with " + hQty + " humans and vz " + vz);
                System.out.println("dt: " + dt);

                Exporter csvExporter = new CsvExporter("output/ovito/diffVz/", String.format(Locale.ROOT, "output_%s_%d_vz_%.1f.csv", hQty, i, vz));
                csvExporter.open();
                Exporter velocityExporter = new VelocityExporter("output/velocity/diffVz/", String.format(Locale.ROOT, "velocity_%s_%d_vz_%.1f.csv", hQty, i, vz));
                velocityExporter.open();

                final Simulator system = new Simulator(entities, dt, beta, tau, csvExporter, velocityExporter);
                system.simulate();

                csvExporter.close();
                velocityExporter.close();
                System.out.println("Simulation " + i + " with " + hQty + " humans and vz " + vz + " finished");
            });
        });
    }
}
