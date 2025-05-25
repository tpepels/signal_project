## Project Members
- Student ID: 6395626
- Student ID: 6400786

## UML Models [uml_models](uml_models)


1. Alert Generation System

    Patient:
    Attributes:
    patientId, patientRecords
    Methods:
    addRecord() – adds a PatientRecord to the list of records.
    Represents an individual in the healthcare system. A Patient can have multiple PatientRecord entries. Aggregation is used between Patient and PatientRecord since records can exist independently of the patient.

    PatientRecord:
    Attributes:
    patientId, recordType, measurementValue, timestamp
    Methods:
    getters for all attributes.
    Represents a single measurement or observation for a patient. Used by the system to track medical data over time.

    DataStorage:
    Attributes:
    patientMap – a mapping from patient IDs to Patient objects
    Methods:
    getAllPatients() – returns a list of all patients
    getRecord() – returns records for a patient in a given time range
    addPatient() – adds a record to an existing patient or creates a new patient if needed
    Manages all patient data in memory. Composed of Patient objects.

    Alert:
    Attributes:
    patientId, condition, timestamp
    Methods:
    getters for all attributes
    Represents a medical alert triggered by an abnormal condition.

    AlertGenerator:
    Attributes:
    dataStorage – a reference to the data storage
    Methods:
    evaluateData(Patient) – evaluates a patient's data for potential alerts
    triggerAlert(Alert) – triggers an alert if a condition is met
    Responsible for analyzing patient data and deciding whether to issue alerts. It uses DataStorage for access to records. Composed within the system.

    AlertManager:
    Attributes: 
    Methods:
    sendAlert(Alert, OutputStrategy, Patient) – sends the alert using the specified output method
    Handles how alerts are delivered or communicated, using different strategies as needed.

2. Data Storage System

    Patient:
    Attributes:
    patientId, patientRecords
    Methods:
    addRecord() – adds a PatientRecord to the internal list
    Represents a patient in the system. Each patient can have multiple medical records. Aggregation is used between Patient and PatientRecord, records can exist without a patient.

    PatientRecord:
    Attributes:
    patientId, recordType, measurementValue, timestamp
    Methods:
    getters for each attribute
    Represents a single medical measurement or event. Used to analyze and retrieve patient data over time.

    DataStorage:
    Attributes:
    patientMap – a map linking patient IDs to their corresponding Patient objects
    Methods:
    getAllPatients() – returns all patients
    getRecord() – returns all records for a specific patient within a given time window
    addPatient() – creates or updates a Patient with a new record
   

    DataRetriever:
    Attributes: 
    Methods:
    getPatient() – fetches a specific patient from DataStorage
    getDataInTimeInterval() – returns patient records of a given type in a time interval
    removePatient() – deletes a patient from the system
    removeRecords() – deletes all records of a certain type within a time range
    Provides operations for accessing and modifying data stored in DataStorage. 



3. Patient Identification System 
 - HospitalPatient: 
      Attributes: patientId, records
      Methods: addRecord() - adds a PatientRecord to the records
      Represent a patient in the Hospital. Patient can have multiple patient records, but records can exist without a patient, so HospitalPatient - PatientRecord uses aggregation.
 - PatientRecord: 
      Attributes: patientId, recordType, measurementValue, timestamp
      Methods: getters for attributes
      Represents a single medical record. It is used by PatientIndentifier to match patient with the record.
 - PatientIndentifier:
      Attributes: matchingCryteria
      Methods: matchData() - tries to find matching patient based on given record, isMatch() - checks if record matches patient based on some rules.
      Handles logic for matching PatientRecords with an existing HospitalPatient. It attempts to match exactly one patient, there might or might not be a match (0 or 1).
 - IndentityManager:
      Attributes: patientDatabase, identifier
      Methods: findPatient() - finds matching patient, handleMismatch() - handles a mismatch (e.g. sends an alert to human stuff or register new patient using registerNewPatient methods), registerNewPatient() - creates new HospitalPatient instance when needed, adds it to patientDataBase, and returns it. 
      Maganes all patients, finds or creates patients based on incoming data. IndentifyManager manages many patients. It owns one PatientIndentifier if Manager dies, so does the Indentifier (composition).

4. Data Access Layer
 - DataListener:
      Common abstract class for all the data sources.
      Methods: listen() - abstract method implemented differently depending on the source (TCP, WebSocket, File).
 - TCP/WebSocket/FileDataListener:
      Attributes: sourceConfig, dataParser, dataSourceAdapter
      Methods: listen() - Retrieves raw data, parses it and passes it to DataSourceAdapter using dataAdapter.recieveRecords().
 - DataParser:
      Attributes: inputFormat (CSV, JSON)
      Methods: parse() - Converts raw string into PatientRecord
      Decouples parsing logic from listeners.
 - DataSourceAdapter:
      Attributes: identityManager, dataStorage
      Methods: recieveRecords() - called by listeners after parsing
      Central adapter between external inputs and internal system.
 - IndentityManager:
      Look Patient Indentification System
 - DataStorage:
      Stores all patient-related data in memory.
   

# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
