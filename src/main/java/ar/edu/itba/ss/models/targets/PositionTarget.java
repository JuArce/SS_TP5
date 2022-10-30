package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Vector;
import lombok.Getter;
import lombok.Setter;


public class PositionTarget implements Target {

    @Getter
    @Setter
    private Vector target;

    public PositionTarget(Vector target) {
        this.target = target;
    }
}
