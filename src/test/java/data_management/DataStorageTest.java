package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class DataStorageTest {

    public class FileDataReader implements DataReader {
        private String outputDirectory;
    
        public FileDataReader(String outputDirectory) {
            this.outputDirectory = outputDirectory;
        }
    
        @Override
        public void readData(DataStorage dataStorage) throws IOException {
            File dir = new File(outputDirectory);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(child))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            dataStorage.storeData(line);
                        }
                    }
                }
            } else {
                throw new IOException("Not a directory or directory does not exist: " + outputDirectory);
            }
        }
    }
    
    interface DataReader {
        void readData(DataStorage dataStorage) throws IOException;
    }
    
    class DataStorage {
        public void storeData(String data) {
            // Implement the logic to store data
            System.out.println("Data Stored: " + data);
        }
    }
}