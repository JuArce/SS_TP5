package ar.edu.itba.ss;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.*;
import ar.edu.itba.ss.models.behavior.HumanBehavior;
import ar.edu.itba.ss.utils.CsvExporter;
import ar.edu.itba.ss.utils.PositionReader;

import java.util.List;
import java.util.stream.Collectors;

import static ar.edu.itba.ss.utils.Constants.*;
import static ar.edu.itba.ss.utils.Random.getRandom;

public class App {

    public static void main(String[] args) {
        final List<Vector> positions = PositionReader.readPositions("src/main/resources/positions.txt");
        final List<Person> entities = positions.stream().map(p -> new Person(p, new Velocity(VD_MAX, 0), PersonState.HUMAN, MAX_RADIUS)).collect(Collectors.toList());
        entities.add(new Person(new Vector(0, 0), new Velocity(0.3, getRandom(0, 2 * Math.PI)), PersonState.ZOMBIE,  MAX_RADIUS));

        final double vdMax = VD_MAX;
        final double ve = VE;
        final double dt = DT;
        final double beta = BETA;
        final double tau = TAU;


        System.out.println("dt: " + dt);

        Exporter exporter = new CsvExporter("", "output.csv");
        exporter.open();

        final Simulator system = new Simulator(entities, dt, beta, tau, exporter);
        system.simulate();

        exporter.close();

    }
}
