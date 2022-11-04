package ar.edu.itba.ss.utils;

public class Constants {

    public static int ITERATIONS = 5000;

    public static double BETA = 0.9;
    public static double TAU = 0.5;

    public static double WALL_RADIUS = 11;

    public static double MIN_RADIUS = 0.15;
    public static double MAX_RADIUS = 0.32;

    public static double VD_MAX = 3; // Max speed
    public static double VE = VD_MAX; // Escape speed
    public static double V_WANDER = 0.3; // Wandering speed
    public static double DOV = 4; // Distance of vision

    public static double DT = MIN_RADIUS / 2 * Math.max(VE, VD_MAX) / 5;





}
