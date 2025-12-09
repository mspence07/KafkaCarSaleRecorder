package com.carsales.recorder.model;

/** Common interface for implementing different Car versions. */
public interface ICar {
  String uuid();

  String make();

  String model();

  String colour();

  double price();
}
