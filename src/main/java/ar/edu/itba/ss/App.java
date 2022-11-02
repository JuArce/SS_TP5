package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.*;
import ar.edu.itba.ss.utils.CsvExporter;
import ar.edu.itba.ss.utils.PositionReader;

import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.itba.ss.utils.Random.getRandom;

public class App {

    public static void main(String[] args) {
        final List<Vector> positions = PositionReader.readPositions("src/main/resources/positions.txt");
        final List<Person> entities = positions.stream().map(p -> new Person(p, new Velocity(0.1, 0), PersonState.HUMAN, Person.MAX_RADIUS)).collect(Collectors.toList());
        entities.add(new Person(new Vector(0, 0), new Velocity(0.3, getRandom(0, 2 * Math.PI)), PersonState.ZOMBIE, Person.MAX_RADIUS));

        final double vdMax = 1.55;
        final double ve = vdMax;
        final double dt = Person.MIN_RADIUS / 2 * Math.max(ve, vdMax);
        final double beta = 0.9;
        final double tau = 0.5;

        Exporter exporter = new CsvExporter("", "output.csv");
        exporter.open();

        final Simulator system = new Simulator(entities, dt, beta, tau, exporter);
        system.simulate();

        exporter.close();

    }
}
