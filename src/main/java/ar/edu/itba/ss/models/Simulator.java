package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;
import lombok.Getter;

import java.util.List;

public class Simulator {

    @Getter
    private final List<Person> entities;
    private final int iterations;
    private final double dt;
    private final double beta;
    private final double tau;
    private final Exporter exporter;

    public Simulator(List<Person> entities, double dt, double beta, double tau, Exporter exporter) {
        this.entities = entities;
        this.iterations = 1000;
        this.dt = dt;
        this.beta = beta;
        this.tau = tau;
        this.exporter = exporter;
    }

    public void simulate() {
        // TODO QUE HACEMOS ACA CON EL TEMA DE QUE HAY QUE CALCULAR TODO Y DESPUES ACCIONAR?
        // CUANDO ES QUE UN ZOMBIE SE LO "COME" ES QUE LLEGA JUSTO A LA POSICION DEL OTRO? O ES QUE SE LO COME CUANDO ESTA CERCA?
        boolean flag = true;
        for (int i = 0; i < iterations && flag; i++) {
            entities.forEach(e -> e.calculateVelocity(entities));
            entities.forEach(e -> e.move(dt));
            flag = entities.stream().filter(p -> p.getState() == PersonState.ZOMBIE).noneMatch(z -> z.getBehavior().isReached());
            exporter.export(this);
        }
    }


}
