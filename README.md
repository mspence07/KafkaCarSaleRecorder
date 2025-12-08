# KafkaCarSaleRecorder

This app demonstrates **schema evolution capabilities**, using the **Confluent Schema Registry** along with the **S3 Sink Connector** to consume Kafka records and push them to **S3 (MinIO)**.

The Java app builds random **Car Sale** POJOs. Based on the supplied **schema version** and **message count**, it uses a **Factory pattern** (`CarSaleGenerator`) to produce different `ICar` implementations (`CarV1` or `CarV2`) and a **Builder pattern** (`CarRecordBuilder`) to construct the corresponding Avro `GenericRecord`. This allows the service to remain agnostic of schema details while supporting multiple schema versions. Polymorphism via the `ICar` interface enables the same code to handle different car versions seamlessly.

The generated records are pushed to a Kafka topic. On the consumer side, the Kafka S3 Connector subscribes to the topic and writes the records to S3. By switching the schema version when invoking the API endpoint, this setup simulates an **evolving schema** and demonstrates **backward compatibility** across different versions.
