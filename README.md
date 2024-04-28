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

### UML Diagram

The following UML diagram illustrates the class structure of the Cardio Data Simulator:

## Alert Generation System:
![image](/UmlDiagrams/Part1/Alert%20Generation%20System.png)

This diagram shows the generation of an alert in the system here.
It allows monitoring our patients so that in case of an emergency,
the system can warn professionals for immediate action.
It starts at the device,
which here is a vital sign monitor, like an ECG or blood pressure monitor,
or any other device that can measure patient data.
Which continuously sends data to the AlertGenerator,
which is responsible for analyzing patient data and generating alerts when certain conditions are met.
The conditions are defined in the PatientProfile class,
which contains information about the patient's ID, vital signs,
and medical history but also the thresholds for generating alerts.
Therefore,
the AlertGenerator can compare the incoming data with the patient's profile,
to determine if an alert should be triggered.
After triggering an alert, if necessary, The AlertGenerator will create an Alert object,
and then send it to the AlertManager for further processing.
The AlertManager will notify the appropriate healthcare professionals or systems about the alert.
The notification here isn't specified.
However, it could be produced by the system in various forms, a message,
an email, or in the case of a hospital, a light or sound alarm like the flat line sound of cardio machines.

## Data Storage System:
![image](/UmlDiagrams/Part1/Data%20Storage%20System.png)

This system represents how the data from patients are stored in the system.
It mainly focuses on how the data is stored and managed in the system.
The DataStorage class is the one that is responsible for storing and managing the data.
Every time the system needs to store date, retrieve it or delete it, it will go through the DataStorage class.
If the goal is to store the data,
the data will first be processed by the AccessControl to check if the user has the right to store the data.
If yes,
then the data will be encrypted by the DataStorage class
then being stored in the Database of the patient in question as a PatientData.

If the goal is to retrieve the data, then it will first go through AccessControl to verify the user's rights.
If the user has the right, the data of the patient needed will be retrieved from the Database.
To do so the system will go through the DataRetriever to get the info of the patient with the ID given.
The patient data is then queried from the Database and go through DataStorage again to be decrypted,
before being sent back to the user as requested.

If the goal is to delete the data, the system will go through the AccessControl to verify the user's rights.
Has a security measure, the check will be more strict than for the other operations.
Then the system will follow the same process as to retrieve the data, but instead of sending the data back to the user,
the data will be deleted from the Database.

During the process, the system will continuously check the authenticity of the user.
This ensures that the data is only accessed by the right people.


## Patient Identification System:
![image](/UmlDiagrams/Part1/Patient%20Identification%20System.png)

This process is about how the system identifies the patients.
This is an important part of the system
as it permits the system to ensure that the data is linked to the correct patient.
Otherwise, a mismatch could occur, and the data could be sent to the wrong patient or even lost.
This would create a lot of issues in terms of safety and privacy.
Also, it is a key part of the system
that would avoid any kind of malpractice or misdiagnosis that could result in the patient's death and a lot of lawsuits
The system starts with the PatientIdentifier class; it is the one receiving the patient's data.
To identify the patient, the system will have to, first go through the IdentityManager class.
This class is responsible for handling discrepancies in the patient's data.
It will check if the patient's data is correct and if it is not, it will correct it.
Then, secondly, the system will go through the HospitalDatabase,
which contains all the patients' data retrieved by their ID.
Finally, the system will compare the data received with the data in the HospitalDatabase with the given data.
In the case of a partial or complete match, the system will return the patient's ID.
If the data doesn't match, the system will return an error message.
This process could not fully function in case of major discrepancies in the data.



## Data Access Layer:
![image](/UmlDiagrams/Part1/Data%20Access%20Layer.png)

The Data Access Layer is the key to how the system interacts with the data.
It is the intermediary between the system and the data.
As in most hospitals, data can be received from various sources and in various formats.
Therefore, this shows a simplified diagram of how the system handles the incoming flux of data.
The data has four major sources:
- TCP
- External Source
- WebSocket
- File

The DataListener class it the one that receives all of those different kind of data, those are in a raw format.
Then the DataListener will send all those different data to the DataParser class.
It is a parser, so it is only an intermediary between the DataListener and the DataSourceAdapter.
The DataSourceAdapter is the one doing the key work.
It is responsible for converting the raw data received by the Listener to a unified format.
Having a unified format is important
as it allows the system to create a structured database without any need for further processing,
nor a need for a more complex data structure.
After the data has been correctly converted to the unified format,
is then sent to the InternalSystem which will then send to the Hospital database to be stored for future use.


## Alert lifecycle:

For the next part of the project, we will be focusing on the alert lifecycle.
The alert lifecycle is the process of how an alert is generated and how it is handled.

![image](/UmlDiagrams/Part2/Alert%20Lifecycle.png)

The state diagram for the alert system is built around the four states that were described in the documentation,
that being generated, sent, acknowledged and resolved.
The state flow is somewhat linear,
with there being a clear transition from the generated state to the final resolved state,
which requires a mix of user interactions and system conditions.
There is, however, a system option to interrupt this cycle at any point, were the alert to become irrelevant.
When the alert is created, due to patient data going over the given threshold, it is in the generated state,
showing that it simply exists without fulfilling any particular mission so far.
Then, it is displayed to all users, which puts it into the sent state,
which indicates that it is idle and waiting for input.
It can then, through said input, be acknowledged by users, who will therefore attend the relevant patient.
It can then, through manual assessment, be resolved by the personnel.
However, at any point throughout this process,
the relevant data falling back underneath the threshold for a preset time will cause the alert
to automatically be resolved.


![image](/UmlDiagrams/Part2/Alert%20Lifecycle%20Sequence.png)

This sequence diagram possesses three main parts,
namely the Alert Generator, the Data Storage, and the individual Alert class.
At regular interval,
the Alert Generator consults the Data Storage to inspect all different values and their relevant thresholds.
If there is an issue with any of them, this kicks in the alert process.
The alert is thus generated and produces a display returned to the generator to be sent to the user.
Upon input from the user, the alert is then acknowledged.
The alert can be resolved in one of two ways, which are both displayed at the end of the sequence for clarity's sake.
First, if at any point the alert generator,
which is still periodically inspecting the data storage detects
that the value relevant to the alert has gone back under the threshold for a prolonged time,
the alert's cycle is interrupted.
Otherwise, once the alert is acknowledged, it can be manually assessed by a user.

## License

This project is licensed under the MIT License—see the [LICENSE](LICENSE) file for details.

## Project Members
- Student ID: i6349778
- Student ID: i6341767
