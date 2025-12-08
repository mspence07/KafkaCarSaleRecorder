package com.carsales.recorder.model;

public record CarV2(
    String uuid, String make, String model, String colour, double price, String status)
    implements ICar {}
