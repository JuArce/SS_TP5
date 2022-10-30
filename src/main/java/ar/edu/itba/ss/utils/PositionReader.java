package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Vector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionReader {

    public static List<Vector> readPositions(String positionPath) {
        try (Stream<String> stream = Files.lines(new File(positionPath).toPath())) {
            return stream.skip(2).map(line -> {
                String[] values = line.split(" ");
                return new Vector(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
