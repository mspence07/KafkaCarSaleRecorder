package com.carsales.recorder.service.impl;

import com.carsales.recorder.avro.CarRecordBuilder;
import com.carsales.recorder.model.ICar;
import com.carsales.recorder.service.ICarSaleService;
import com.carsales.recorder.util.CarSaleGenerator;
import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarSaleService implements ICarSaleService {

  @Autowired private KafkaTemplate<String, GenericRecord> kafkaTemplate;

  @Override
  public void generateSaleRecords(String schemaVersion, int messageCount) {

    List<GenericRecord> records = new ArrayList<>();

    for (int i = 0; i < messageCount; i++) {

      ICar car =
          "v2".equalsIgnoreCase(schemaVersion)
              ? CarSaleGenerator.generateRandomV2Car()
              : CarSaleGenerator.generateRandomV1Car();

      GenericRecord record =
          CarRecordBuilder.builder().car(car).schemaVersion(schemaVersion).build();

      System.out.println("record is " + record.toString());

      kafkaTemplate.send("carsales", record);

      records.add(record);
    }
    for (GenericRecord record : records) {
      System.out.println("record is " + record.toString());
    }
  }
}
