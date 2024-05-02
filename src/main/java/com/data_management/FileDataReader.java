package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDataReader implements DataReader{

    private String baseDirectory;

    /** constructs a FileDataReader that reads from the specified directory
     * 
     * @param baseDirectory the base directory where the data files is stored
     */
    public FileDataReader(String baseDirectory){
        this.baseDirectory = baseDirectory;
    } 

    /**
     * Reads data from a specified file and stores it in the data storage.
     * 
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        
        // finding the directory
        if(!Files.isDirectory(Paths.get(baseDirectory))){
            throw new IOException("Error finding base directory");
        }
        
        //retrieve all filepaths within the directory
        Set<Path> filePathList = listFiles(baseDirectory);
        if(filePathList.size() == 0){
            throw new IOException("Empty directory");
        }

        //read files line by line
        for (Path filePath : filePathList) {
            try (BufferedReader reader = Files.newBufferedReader(filePath);) {
                String line = reader.readLine();
                while(line!=null){
                    String[] patientData = line.split(",");
                    dataStorage.addPatientData( Integer.parseInt(patientData[0]),   //patient ID
                                                Double.parseDouble(patientData[3]), //data
                                                patientData[2],                     //label
                                                Long.parseLong(patientData[1]));    //timestamp
                }
                
            } catch (IOException e) {
                System.err.println("Error while opening file " + filePath + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error while parsing data in file " + filePath + ": " + e.getMessage());
            }
            
        }

    }

    /** lists all files within a base directory. Does not include files of sub-directories.
     * 
     * @param baseDirectory the base directory where the data files is stored
     * @return the set of all filepaths in the base directory
     * @throws IOException
     */
    private Set<Path> listFiles(String baseDirectory) throws IOException {
    try (Stream<Path> stream = Files.list(Paths.get(baseDirectory))) {
        return stream
          .filter(file -> !Files.isDirectory(file))
          .map(Path::getFileName)
          .collect(Collectors.toSet());
    }
}
    
}
