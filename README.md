# KafkaCarSaleRecorder

This app demonstrates **schema evolution capabilities**, using the **Confluent Schema Registry** along with the **S3 Sink Connector** to consume Kafka records and push them to **S3 (MinIO)**.

The Java app builds random **Car Sale** POJOs. Based on the supplied **schema version** and **message count**, it uses a **Factory pattern** (`CarSaleGenerator`) to produce different `ICar` implementations (`CarV1` or `CarV2`) and a **Builder pattern** (`CarRecordBuilder`) to construct the corresponding Avro `GenericRecord`. This allows the service to remain agnostic of schema details while supporting multiple schema versions. Polymorphism via the `ICar` interface enables the same code to handle different car versions seamlessly.

The generated records are pushed to a Kafka topic. On the consumer side, the Kafka S3 Connector subscribes to the topic and writes the records to S3. By switching the schema version when invoking the API endpoint, this setup simulates an **evolving schema** and demonstrates **backward compatibility** across different versions.

## Example Usage

Below are some sample commands demonstrating the app working with Schema Registry and JSON output from MinIO S3. To run the app, you will need Docker installed.

### Start the app and dependencies

```bash
docker compose up -d
```

### Access MinIO UI

Navigate to [http://localhost:9000](http://localhost:9000).
Default credentials:

* **User:** `minioadmin`
* **Password:** `minio123`

---

### Generate new records (schema version v1)

Send a POST request to the `/generate` endpoint with `schemaVersion` set to `v1` and `messageCount` set to 5:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"schemaVersion":"v1", "messageCount":5}' \
  http://localhost:8080/generate
```

Check the latest schema in the Schema Registry (v1 schema omits the `status` field):

```bash
curl -s http://localhost:8081/subjects/carsales-value/versions/latest
```

Example response:

```json
{
  "subject": "carsales-value",
  "version": 1,
  "id": 3,
  "guid": "2040d6b7-ff98-3593-3639-217293a2dc5c",
  "schemaType": "AVRO",
  "schema": "{\"type\":\"record\",\"name\":\"Car\",\"namespace\":\"com.carsales.recorder.avro\",\"fields\":[{\"name\":\"uuid\",\"type\":\"string\"},{\"name\":\"make\",\"type\":\"string\"},{\"name\":\"model\",\"type\":\"string\"},{\"name\":\"colour\",\"type\":\"string\"},{\"name\":\"price\",\"type\":\"double\"}]}",
  "ts": 1765288224077,
  "deleted": false
}
```

You can also download one of the JSON files from MinIO to examine the contents:

```json
{"uuid":"lxz6194","make":"Tesla","model":"Model 3","colour":"Blue","price":11627.75720852044}
{"uuid":"iai7595","make":"Tesla","model":"Cybertruck","colour":"Blue","price":14570.907862358983}
{"uuid":"avi9173","make":"Audi","model":"A4","colour":"Grey","price":24084.747613330648}
```

---

### Generate new records (schema version v2)

Send another request using `schemaVersion` set to `v2`:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"schemaVersion":"v2", "messageCount":5}' \
  http://localhost:8080/generate
```

Check the latest schema in the Schema Registry again — now it includes the `status` field:

```bash
curl -s http://localhost:8081/subjects/carsales-value/versions/latest
```

Example response:

```json
{
  "subject": "carsales-value",
  "version": 2,
  "id": 4,
  "guid": "efb2ef19-77d9-1878-1b4e-1b608f2579ff",
  "schemaType": "AVRO",
  "schema": "{\"type\":\"record\",\"name\":\"Car\",\"namespace\":\"com.carsales.recorder.avro\",\"fields\":[{\"name\":\"uuid\",\"type\":\"string\"},{\"name\":\"make\",\"type\":\"string\"},{\"name\":\"model\",\"type\":\"string\"},{\"name\":\"colour\",\"type\":\"string\"},{\"name\":\"price\",\"type\":\"double\"},{\"name\":\"status\",\"type\":\"string\",\"default\":\"FOR_SALE\"}]}",
  "ts": 1765288296372,
  "deleted": false
}
```

Downloaded JSON files now include the `status` field:

```json
{"uuid":"vaw6317","make":"SEAT","model":"Ateca","colour":"Yellow","price":30242.17351245774,"status":"SOLD"}
{"uuid":"jaz4638","make":"SAAB","model":"900","colour":"White","price":21383.97053316392,"status":"FOR_SALE"}
{"uuid":"vlg3218","make":"SEAT","model":"Leon","colour":"Red","price":13597.2647996802,"status":"SOLD"}
```

---

### Backward compatibility

Since the connector is configured in **backward compatible mode**, issuing another request using the v1 schema is fully supported by the Schema Registry.

## TODO

* Health checks
* Logging
* Exception handling
* Unit testing
* Return a meaningful response instead of System.out statements

