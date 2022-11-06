package ar.edu.itba.ss.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
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

    private double getModule() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public double distanceTo(Vector other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public double angleTo(Vector other) {
        if (Double.compare(this.getModule(), 0) == 0 || Double.compare(other.getModule(), 0) == 0) {
            return 0;
        }
        double arg = this.dotProduct(other) / (this.getModule() * other.getModule());
        if (arg > 1) {
            arg = 1;
        } else if (arg < -1) {
            arg = -1;
        }
        return Math.acos(arg);
    }

    public Vector directionTo(Vector other) {
        if (this.equals(other)) {
            return new Vector(0, 0);
        }
        final double x = other.x - this.x;
        final double y = other.y - this.y;
        return new Vector(x, y).normalize();
    }

    public Vector rotate(double angle) {
        final double x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        final double y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector normalize() {
        final double module = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        this.x /= module;
        this.y /= module;
        return this;
    }

    private double dotProduct(Vector other) {
        return this.x * other.x + this.y * other.y;
    }

    public static Vector sum(List<Vector> vectors) {
        double x = 0;
        double y = 0;
        for (Vector v : vectors) {
            x += v.x;
            y += v.y;
        }
        return new Vector(x, y);
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
