package ar.edu.itba.ss.models;

import lombok.Getter;
import lombok.Setter;

public class Velocity {

    @Getter
    @Setter
    private double module;

    @Getter
    @Setter
    private double angle;

    public Velocity(double module, double angle) {
        this.module = module;
        this.angle = angle;
    }

    public double getVelocityX() {
        return module * Math.cos(angle);
    }

    public double getVelocityY() {
        return module * Math.sin(angle);
    }

    @Override
    public String toString() {
        return "module: " + module + ", angle: " + angle;
    }
}
