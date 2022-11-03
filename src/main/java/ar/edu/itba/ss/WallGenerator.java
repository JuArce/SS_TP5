package ar.edu.itba.ss;

import com.opencsv.CSVWriter;

import java.io.FileWriter;

public class WallGenerator {
    public static void main(String[] args) {
        final double r = 11;
        final double step = 0.0001;
        final int particles = (int) (2 * Math.PI / step) + 1;
        final String fullPath = "src/main/resources/walls.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(fullPath), ' ', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
            writer.writeNext(new String[]{particles + ""});
            writer.writeNext(new String[]{"x", "y"});
            for (double i = 0; i < 2 * Math.PI; i += step) {
                writer.writeNext(new String[]{
                        (r * Math.cos(i)) + "",
                        (r * Math.sin(i)) + ""
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
