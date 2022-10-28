package ar.edu.itba.ss.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Vector {
    @Getter
    @Setter
    private double x;
    @Getter @Setter
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Vector other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
