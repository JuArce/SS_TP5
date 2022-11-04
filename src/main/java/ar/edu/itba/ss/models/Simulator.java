package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.behavior.ZombieBehavior;
import lombok.Getter;

import java.util.List;

import static ar.edu.itba.ss.utils.Constants.ITERATIONS;
import static ar.edu.itba.ss.utils.Constants.MAX_RADIUS;

public class Simulator {

    @Getter
    private final List<Person> entities;
    private final int iterations;
    public final double dt;
    private final double beta;
    private final double tau;
    private final Exporter exporter;

    public static double DELTA_R;

    public Simulator(List<Person> entities, double dt, double beta, double tau, Exporter exporter) {
        this.entities = entities;
        this.iterations = ITERATIONS;
        this.dt = dt;
        this.beta = beta;
        this.tau = tau;
        this.exporter = exporter;
        DELTA_R = MAX_RADIUS / (tau / dt);
    }

    public void simulate() {
        for (int i = 0; i < iterations; i++) {
            exporter.export(this);
            entities.forEach(e -> e.calculateVelocity(entities));
            entities.forEach(Person::execute);
            entities.forEach(e-> e.move(dt));
        }
    }


}
