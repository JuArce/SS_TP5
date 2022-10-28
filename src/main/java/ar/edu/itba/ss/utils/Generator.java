package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Vector;
import com.opencsv.CSVWriter;
import lombok.Setter;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    private static final String BASE_PATH = "src/main/resources/";
    private final double minRadius;
    private final double maxRadius;
    @Setter
    private int persons;

    public Generator(double minRadius, double maxRadius, int persons) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.persons = persons;
    }

    public List<Vector> generate(String filename) {
        final List<Vector> positions = new ArrayList<>();
        int i = 0;
        while(i < persons) {
            final double mod = getRandom(minRadius, maxRadius);
            final double angle = getRandom(0, 2 * Math.PI);
            final double x = mod * Math.cos(angle);
            final double y = mod * Math.sin(angle);
            final Vector position = new Vector(x, y);
            if (!positions.contains(position)) {
                positions.add(position);
                i++;
            }
        }
        exportPositions(filename, positions);
        return positions;
    }

    private void exportPositions(String filename, List<Vector> positions) {
        try {
            final CSVWriter writer = new CSVWriter(new FileWriter(BASE_PATH + filename), ' ', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(new String[]{String.valueOf(positions.size())});
            writer.writeNext(new String[]{"x", "y"});
            positions.forEach(position ->
                    writer.writeNext(new String[]{String.valueOf(position.getX()), String.valueOf(position.getY())}));
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double getRandom(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
