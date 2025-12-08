package com.carsales.recorder.controller;

import com.carsales.recorder.model.SaleRequest;
import com.carsales.recorder.service.ICarSaleService;
import com.carsales.recorder.util.CarSaleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarSaleController {

  @Autowired private ICarSaleService carSaleService;

  @GetMapping("/")
  public String index() {
    return "Greetings from Spring boot!";
  }

  @GetMapping("/random-car")
  public String randomCar() {
    String randomMake = CarSaleGenerator.getRandomMake();
    String randomModel = CarSaleGenerator.getRandomModel(randomMake);

    return "Car is " + randomMake + " " + randomModel;
  }

  @PostMapping("/generate")
  public String generateSalesRecords(@RequestBody SaleRequest saleRequest) {
    carSaleService.generateSaleRecords(saleRequest.schemaVersion(), saleRequest.messageCount());

    return "Generated " + saleRequest.messageCount() + " messages and put onto a Kafka topic!";
  }
}
