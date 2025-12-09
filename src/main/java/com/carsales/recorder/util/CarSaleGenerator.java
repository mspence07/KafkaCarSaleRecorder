package com.carsales.recorder.util;

import com.carsales.recorder.model.CarV1;
import com.carsales.recorder.model.CarV2;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/** Utility for random generation of Car data. */
public class CarSaleGenerator {

  private static final Map<String, List<String>> CARS =
      Map.of(
          "Ford", List.of("Capri", "Fiesta", "Focus", "Mustang"),
          "BMW", List.of("M2", "M3", "X3", "M140i"),
          "SAAB", List.of("93", "95", "900"),
          "SEAT", List.of("Ibiza", "Leon", "Ateca"),
          "Audi", List.of("A3", "A4", "A5", "A6"),
          "Tesla", List.of("Model 3", "Model Y", "Cybertruck"),
          "Kia", List.of("Ceed", "EV6", "Picanto", "Sportage"));

  private static final List<String> COLOURS =
      List.of("Red", "Blue", "Green", "Black", "White", "Grey", "Yellow");

  public static CarV1 generateRandomV1Car() {
    final String uuid = getRandomNiReg();
    final String make = getRandomMake();
    final String model = getRandomModel(make);
    final String colour = COLOURS.get(ThreadLocalRandom.current().nextInt(COLOURS.size()));
    double price = ThreadLocalRandom.current().nextDouble(10000, 40000);

    return new CarV1(uuid, make, model, colour, price);
  }

  public static CarV2 generateRandomV2Car() {
    CarV1 base = generateRandomV1Car();
    final String status = ThreadLocalRandom.current().nextBoolean() ? "SOLD" : "FOR_SALE";
    return new CarV2(base.uuid(), base.make(), base.model(), base.colour(), base.price(), status);
  }

  private static String getRandomMake() {
    List<String> makes = CARS.keySet().stream().toList();

    return makes.get(ThreadLocalRandom.current().nextInt(makes.size()));
  }

  private static String getRandomModel(final String make) {
    List<String> models = CARS.get(make);
    return models.get(ThreadLocalRandom.current().nextInt(models.size()));
  }

  private static String getRandomNiReg() {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    final char[] firstTwoChars = "abcdefghijklmnoprstuvwxy".toCharArray();
    final char[] lastChar = "giwz".toCharArray();

    char first = firstTwoChars[random.nextInt(firstTwoChars.length)];
    char second = firstTwoChars[random.nextInt(firstTwoChars.length)];
    char third = lastChar[random.nextInt(lastChar.length)];

    int numbers = random.nextInt(1001, 9999);

    return new StringBuilder()
        .append(first)
        .append(second)
        .append(third)
        .append(numbers)
        .toString();
  }
}
