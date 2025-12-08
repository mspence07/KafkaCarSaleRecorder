package com.carsales.recorder.model;

public record CarV1(String uuid, String make, String model, String colour, double price)
    implements ICar {}
