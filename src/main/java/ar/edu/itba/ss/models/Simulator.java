package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Exporter;
import lombok.Getter;

import java.util.List;

public class Simulator {

    @Getter
    private final List<Person> people;
    private final int iterations;
    private final double dt;
    private final double beta;
    private final double tau;
    private final Exporter exporter;

    public Simulator(List<Person> people, double dt, double beta, double tau, Exporter exporter) {
        this.people = people;
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
            List<Person> zombies = people.stream().filter(p -> p.getState() == PersonState.ZOMBIE).toList();
            zombies.forEach(z -> z.calculateTarget(people.stream().filter(p -> p.getState() == PersonState.HUMAN).toList()));
            zombies.forEach(z->z.move(dt));
            flag = zombies.stream().noneMatch(z -> z.getBehavior().isReached(z));
            exporter.export(this);
        }
    }


}
