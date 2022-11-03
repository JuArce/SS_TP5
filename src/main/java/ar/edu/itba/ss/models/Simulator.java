package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.behavior.ZombieBehavior;
import lombok.Getter;

import java.util.List;

public class Simulator {

    @Getter
    private final List<Person> entities;
    private final int iterations;
    public static double dt;
    private final double beta;
    private final double tau;
    private final Exporter exporter;

    public static double DELTA_R;

    public Simulator(List<Person> entities, double dt, double beta, double tau, Exporter exporter) {
        this.entities = entities;
        this.iterations = 10000;
        this.dt = dt;
        this.beta = beta;
        this.tau = tau;
        this.exporter = exporter;
        DELTA_R = Person.MAX_RADIUS / (tau / dt);
    }

    public void simulate() {
        // TODO QUE HACEMOS ACA CON EL TEMA DE QUE HAY QUE CALCULAR TODO Y DESPUES ACCIONAR?
        // CUANDO ES QUE UN ZOMBIE SE LO "COME" ES QUE LLEGA JUSTO A LA POSICION DEL OTRO? O ES QUE SE LO COME CUANDO ESTA CERCA?
        boolean flag = true;
        for (int i = 0; i < iterations; i++) {
            exporter.export(this);
            entities.forEach(e -> e.calculateVelocity(entities));
            entities.forEach(Person::execute);
            entities.forEach(e-> e.move(dt));
        }
    }


}
