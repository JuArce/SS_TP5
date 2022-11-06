package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;
import lombok.Getter;

import java.util.List;

public class Simulator {

    @Getter
    private final List<Person> entities;

    private final int iterations;

    private final Exporter csvExporter;
    private final Exporter velocityExporter;

    public static double dt;
    public static double BETA;
    public static double DELTA_R;

    public Simulator(List<Person> entities, double dt, double beta, double tau, Exporter csvExporter, Exporter velocityExporter) {
        this.entities = entities;
        this.iterations = 10000;

        this.csvExporter = csvExporter;
        this.velocityExporter = velocityExporter;

        Simulator.dt = dt;
        Simulator.BETA = beta;
        DELTA_R = Person.MAX_RADIUS / (tau / dt);
    }

    public void simulate() {
        for (int i = 0; i < iterations; i++) {
            if (i % 5 == 0) {
                csvExporter.export(this);
            }
            velocityExporter.export(this);
            entities.forEach(e -> e.calculateVelocity(entities));
            entities.forEach(Person::execute);
            entities.forEach(e-> e.move(dt));
        }
    }


}
