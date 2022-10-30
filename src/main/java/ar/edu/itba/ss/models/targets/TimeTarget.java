package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import lombok.Getter;

public class TimeTarget implements Target {

    @Getter
    private final double target;
    private double counter;

    public TimeTarget(double target) {
        this.target = target;
        this.counter = 0;
    }

    public void update(double dt) {
        counter += dt;
    }

    public boolean isReached() {
        return target <= counter;
    }
}
