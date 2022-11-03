package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.Simulator;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/";

    private final String fullPath;
    private CSVWriter csvWriterAppender;

    public CsvExporter(String path, String filename) {
        this.fullPath = baseFilename + path + filename;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fullPath));
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(fullPath, true), ' ', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(Simulator simulator) {
        List<Person> people = simulator.getEntities();
        try {
            csvWriterAppender.writeNext(new String[]{people.size() + ""});
            csvWriterAppender.writeNext(new String[]{"x", "y", "mod", "angle", "state"});
            for (Person person : people) {
                csvWriterAppender.writeNext(new String[]{
                        person.getRadius() + "",
                        person.getPosition().getX() + "",
                        person.getPosition().getY() + "",
                        person.getVelocity().getModule() + "",
                        person.getVelocity().getAngle() + "",
                        person.getState().toString()
                });
            }
        } catch (Exception e) {
            e.printStackTrace(); //TODO: handle exception
        }
    }

    @Override
    public void close() {
        try {
            csvWriterAppender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
