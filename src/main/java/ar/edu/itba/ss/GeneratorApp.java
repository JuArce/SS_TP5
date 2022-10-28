package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Vector;
import ar.edu.itba.ss.utils.Generator;

import java.util.List;

/**
 *
 */
public class GeneratorApp {
    public static void main(String[] args) {
        Generator generator = new Generator(1, 11, 20000);
        List<Vector> positions = generator.generate("positions.txt");
        System.out.println("Positions generated");
    }
}
