package com.carsales.recorder.model;

/**
 * Record for holding information about a CarV2 Schema.
 *
 * @param uuid
 * @param make
 * @param model
 * @param colour
 * @param price
 * @param status
 */
public record CarV2(
    String uuid, String make, String model, String colour, double price, String status)
    implements ICar {}
