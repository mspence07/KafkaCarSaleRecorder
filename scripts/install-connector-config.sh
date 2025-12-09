#!/bin/bash
echo "Waiting for Kafka Connect to be ready..."

until curl -sf http://localhost:8083/connectors; do
  sleep 5
done

echo "Kafka Connect is ready. Applying connector config..."
curl -X PUT -H "Content-Type: application/json" \
   --data @/connectors/s3-sink.json \
  http://localhost:8083/connectors/s3-sink-cars/config