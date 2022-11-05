package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;
import lombok.Getter;

import java.util.List;

import static ar.edu.itba.ss.utils.Constants.ITERATIONS;
import static ar.edu.itba.ss.utils.Constants.MAX_RADIUS;

public class Simulator {

    @Getter
    private final List<Person> entities;
    private final int iterations;
    @Getter
    public final double dt;
    private final double beta;
    private final double tau;
    private final Exporter csvExporter;
    private final Exporter velocityExporter;

    public static double DELTA_R;

    public Simulator(List<Person> entities, double dt, double beta, double tau, Exporter csvExporter, Exporter velocityExporter) {
        this.entities = entities;
        this.iterations = ITERATIONS;
        this.dt = dt;
        this.beta = beta;
        this.tau = tau;
        this.csvExporter = csvExporter;
        this.velocityExporter = velocityExporter;
        DELTA_R = MAX_RADIUS / (tau / dt);
    }

    public void simulate() {
        for (int i = 0; i < iterations; i++) {
            csvExporter.export(this);
            velocityExporter.export(this);
            entities.forEach(e -> e.calculateVelocity(entities));
            entities.forEach(Person::execute);
            entities.forEach(e-> e.move(dt));
        }
    }


}
