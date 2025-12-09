package com.carsales.recorder.model;

/**
 * Record for holding information about a CarV1 Schema.
 *
 * @param uuid
 * @param make
 * @param model
 * @param colour
 * @param price
 */
public record CarV1(String uuid, String make, String model, String colour, double price)
    implements ICar {}
