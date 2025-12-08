# KafkaCarSaleRecorder

This Spring Boot application demonstrates **schema evolution capabilities** using the Confluent Schema Registry along with the Kafka S3 Sink Connector to consume Kafka records and push them to S3 (MinIO).

The Java app generates random **Car Sale POJOs** and, based on a supplied schema version and message count, uses a **Factory pattern** to produce Avro `GenericRecords` corresponding to that schema version. The factory encapsulates the construction of the Avro records, so the service code does not need to know the details of each schema version. These records are then published to a Kafka topic.

On the consumer side, the **Kafka S3 Sink Connector** subscribes to the topic and writes the records to S3. By switching the schema version when invoking the API endpoint, this setup simulates **schema evolution** in real-time and demonstrates how **backward compatibility** is maintained via the Confluent Schema Registry.

