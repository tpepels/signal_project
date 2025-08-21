1. Alert Generation System
This part of the system is responsible for spotting medical problems in the data and making sure the right people are notified. It works by checking each new data point to see if anything looks abnormal—like a heart rate that’s too high or oxygen levels that are too low.
The AlertGenerator class does this monitoring. When it finds something that needs attention, it creates an Alert. This alert includes the patient ID, the problem it found, and the time it happened. Each alert is specific, so staff can respond quickly and confidently.
Once an alert is created, the AlertManager steps in. This class makes sure the alert is sent to the right place, whether that’s a hospital dashboard, a nurse’s mobile device, or an internal messaging system. It helps make sure that no important warning goes unnoticed.
The system connects closely with PatientData and DataStorage, so alerts are always based on real, up-to-date information. This avoids confusion or false warnings. By separating the job of creating alerts from the job of sending them, the system stays flexible. It’s easy to update how alerts are delivered, or to add smarter rules for when alerts should be triggered, without affecting the rest of the system.

2. Data Storage System
This diagram focuses on collecting, storing, and retrieving patient data in a secure and organized way. Incoming data is stored using the DataStorage interface. Each data point is represented as a PatientData object, which includes the patient ID, timestamp, and the type of measurement, such as heart rate or oxygen saturation.
The Patient class maintains a list of PatientRecord entries for a specific patient. These records make it easy to retrieve historical data within a selected time range. The system supports both real-time monitoring and longer-term analysis.
The DataRetriever handles requests from medical staff who need to access patient information. It ensures access control is enforced, so only authorized users can view patient data. This keeps the system aligned with privacy and security standards.
To avoid overloading the system with old data, DataStorage supports deletion policies, where outdated records are automatically removed after a set number of days. This keeps storage efficient and manageable over time.

3. Patient Identification System
This diagram ensures that incoming patient data is matched correctly to hospital records. When data enters the system, the PatientIdentifier checks if the patient ID exists in the hospital database. If it finds a match, the system links the data to the correct HospitalPatient, which holds the patient’s details such as name and medical history.
If a match cannot be found, the IdentityManager takes over. It handles these exceptions by logging the problem, notifying staff, or discarding the data, depending on how the system is configured. This helps prevent data from being misassigned, which is critical for patient safety.
The goal is to keep the matching logic separate from data storage and alerts. That way, if the hospital updates how it manages patient records, only the matching part of the system needs to change.

4. Data Access Layer
This part of the design handles incoming data from outside sources like files, network ports, or live feeds. To keep the system flexible, it uses the DataListener interface. There are different listener types for each input method: TCPDataListener, WebSocketDataListener, and FileDataListener.
Each listener receives raw data, which is then passed to a DataParser. This parser turns the raw input into a standard format, typically as PatientData objects. This makes sure that no matter how the data comes in—JSON, CSV, or another format—the rest of the system can work with it consistently.
After parsing, the DataSourceAdapter takes the cleaned-up data and passes it to the storage layer. This helps keep the parsing and input logic separate from how data is stored or analyzed.
