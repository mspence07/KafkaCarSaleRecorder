package com.carsales.recorder.service.impl;

import com.carsales.recorder.avro.CarRecordBuilder;
import com.carsales.recorder.model.ICar;
import com.carsales.recorder.service.ICarSaleService;
import com.carsales.recorder.util.CarSaleGenerator;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
/**
 * Service class for handling the build of a Car record and sending that record to a Kafka topic
 * using a Spring implementation of a Kafka producer.
 */
public class CarSaleService implements ICarSaleService {

  @Autowired private KafkaTemplate<String, GenericRecord> kafkaTemplate;

  @Override
  public void generateSaleRecords(final String schemaVersion, final int messageCount) {

    for (int i = 0; i < messageCount; i++) {
      ICar car =
          "v2".equalsIgnoreCase(schemaVersion)
              ? CarSaleGenerator.generateRandomV2Car()
              : CarSaleGenerator.generateRandomV1Car();

      GenericRecord record =
          CarRecordBuilder.builder().car(car).schemaVersion(schemaVersion).build();

      kafkaTemplate.send("carsales", record);
    }
  }
}
