package com.datamanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class ReadData implements DataReader{
    private final Path directory;

    /** @param outputDir directory created by --output file:<output_dir> */
    public ReadData (String outputDir) {
        this.directory = Paths.get(outputDir);
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        if (!Files.exists(directory)) {
            throw new IOException("Directory does not exist");
        }
        try (BufferedReader reader = Files.newBufferedReader(directory)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                List<String> record = Arrays.asList(line.split("\\s*[:,]\\s*"));
                if (record.size() == 8)
                    insertPatientData(record.get(1), record.get(7), record.get(5), record.get(3), dataStorage, line);
            }
        }
        catch (IOException e) {
            throw new IOException(e);
        }
    }

    /* Method checks if the format of the data is valid and then adds the patient data if it is */
    private void insertPatientData(Object patientId,
                                        Object measurementValue,
                                        Object recordType,
                                        Object timestamp,
                                        DataStorage storage,
                                        String rawLine) {
        try {
            int    id    = Integer.parseInt(patientId.toString());
            // I decided to skip the Alert labels. The method addPatientData takes
            // in doubles, not Object
            String type  = recordType.toString().trim();
            long   time  = Long.parseLong(timestamp.toString());

            if (!type.isEmpty())        // domain rule
                storage.addPatientData(id, measurementValue, type, time);
            // everything went fine
        } catch (Exception e) {
            System.err.println("Skipped bad line → " + rawLine +
                    " ; reason: " + e.getMessage());
        }
    }
}
