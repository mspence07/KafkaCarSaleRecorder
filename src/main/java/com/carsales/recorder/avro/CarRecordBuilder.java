package com.carsales.recorder.avro;

import com.carsales.recorder.model.CarV1;
import com.carsales.recorder.model.CarV2;
import com.carsales.recorder.model.ICar;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;

/** Builder class to construct avro records for a given car schema version. */
public class CarRecordBuilder {

  private ICar car;
  private String schemaVersion;

  public static CarRecordBuilder builder() {
    return new CarRecordBuilder();
  }

  public CarRecordBuilder car(ICar car) {
    this.car = car;
    return this;
  }

  public CarRecordBuilder schemaVersion(final String schemaVersion) {
    this.schemaVersion = schemaVersion;
    return this;
  }

  public GenericRecord build() {
    if (car == null || schemaVersion == null) {
      throw new IllegalStateException("Car and schema version must be set before building");
    }

    switch (schemaVersion.toLowerCase()) {
      case "v1":
        return buildV1Record((CarV1) car);
      case "v2":
        return buildV2Record((CarV2) car);
      default:
        throw new IllegalArgumentException("Unknown schema version: " + schemaVersion);
    }
  }

  private GenericRecord buildV1Record(CarV1 carV1) {
    Schema schema = ReflectData.get().getSchema(CarV1.class);
    GenericRecord record = new GenericData.Record(schema);
    record.put("uuid", carV1.uuid());
    record.put("make", carV1.make());
    record.put("model", carV1.model());
    record.put("colour", carV1.colour());
    record.put("price", carV1.price());
    return record;
  }

  private GenericRecord buildV2Record(CarV2 carV2) {
    Schema schema = ReflectData.get().getSchema(CarV2.class);
    GenericRecord record = new GenericData.Record(schema);
    record.put("uuid", carV2.uuid());
    record.put("make", carV2.make());
    record.put("model", carV2.model());
    record.put("colour", carV2.colour());
    record.put("price", carV2.price());
    record.put("status", carV2.status());
    return record;
  }
}
