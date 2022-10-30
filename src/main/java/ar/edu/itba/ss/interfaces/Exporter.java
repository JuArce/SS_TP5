package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Simulator;

public interface Exporter {

    void open();

    void export(Simulator simulator);

    void close();

}
