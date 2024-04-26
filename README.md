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
java -jar target/target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Project Members
- Student ID: i6360797
- Student ID: i6342536


## Description of UML State Diagram for an Alert
The UML State Diagram for an Alert in the Alert Generation System provides a visual representation of the various states an alert can occupy from inception to conclusion. This diagram is instrumental in illustrating the lifecycle of an alert, delineating the progression from the "Generated" state to the "Resolved" state, with intermediate states of "Sent" and "Acknowledged."

### Generated State: 
The lifecycle of an alert begins in the "Generated" state. This state is entered when the system detects that patient data has crossed a predefined threshold. For instance, an alert may be generated if a patient's heart rate exceeds a certain limit. The state transition occurs due to a system-triggered event based on real-time data monitoring.
Sent State: Once an alert is generated, it transitions to the "Sent" state. In this state, the alert is actively communicated to the medical staff through various channels such as mobile devices or workstation monitors. This ensures that the alert captures the immediate attention of the medical staff, prompting timely intervention.
### Acknowledged State:
Following the alert's receipt, it enters the "Acknowledged" state when a medical staff member acknowledges the alert. Acknowledgment can occur manually by clicking on the alert or automatically when the staff views the patient’s detailed data. This state is crucial as it prevents the re-sending of the same alert, thereby reducing redundancy and alert fatigue.
### Resolved State: 
The final state in the alert lifecycle is "Resolved." An alert can be resolved in two ways: automatically, if subsequent patient data indicate normal conditions over a specified period, or manually, by a medical staff member after assessing and potentially modifying the patient’s treatment.

## Description of UML Sequence Diagram for the Alert Generation System
The UML Sequence Diagram for the Alert Generation System effectively maps out the interactions and processes involved in detecting, generating, and managing alerts based on patient data that meets specific health risk criteria. This diagram serves as a comprehensive blueprint for developers and stakeholders to understand the flow of data and responsibilities across various system components.

Initial Data Transmission: The sequence begins with a "Heart Rate Monitor" transmitting patient data to the "AlertGenerator." This data is crucial as it triggers the evaluation process for potential alerts.
Data Evaluation: Upon receiving the data, the "AlertGenerator" interacts with the "DataStorage" system to retrieve historical patient data. This historical data is essential to determine if the detected condition represents an ongoing trend or an isolated event.
Alert Generation: If the evaluation confirms that the data meets the alert thresholds, the "AlertGenerator" proceeds to create an alert. This creation is depicted in the diagram as a transition from data evaluation to alert instantiation.
Alert Notification and Acknowledgment: Once the alert is generated, it is sent to the medical staff, specifically represented by a "Nurse" in the diagram. The nurse receives the alert notification on their device, acknowledges it, and may access the patient's detailed view to assess the situation further.
Alert Resolution: After necessary interventions, the nurse has the capability to resolve the alert manually in the system. Resolution can also occur automatically if subsequent data shows the patient's condition has stabilized.

link to uml_models: