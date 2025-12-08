package com.carsales.recorder.service;

public interface ICarSaleService {

  /**
   * Builds car sale records and puts onto a Kafka topic.
   *
   * @param schemaVersion - Schema version
   * @param messageCount - Number of sale records to generate.
   */
  void generateSaleRecords(final String schemaVersion, final int messageCount);
}
