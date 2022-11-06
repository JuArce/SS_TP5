package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.interfaces.Exporter;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Simulator;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class VariationExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/";

    private final String fullPath;
    private CSVWriter csvWriterAppender;
    private int lastZombies = 1;

    public VariationExporter(String path, String filename) {
        this.fullPath = baseFilename + path + filename;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fullPath));
            writer.writeNext(new String[]{"Time", "Variation"});
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(fullPath, true), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(Simulator simulator) {
        try {
            final int currentZombies = (int) simulator.getEntities().stream()
                    .filter(e -> e.getState() == PersonState.ZOMBIE || e.getState() == PersonState.TRANSITIONING)
                    .count();
            final int variation = currentZombies - lastZombies;
            final double t = Simulator.dt * simulator.getI();

            csvWriterAppender.writeNext(new String[]{
                    t + "",
                    variation + ""
            });
            lastZombies = currentZombies;
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
