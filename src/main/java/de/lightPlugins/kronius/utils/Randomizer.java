package de.lightPlugins.kronius.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    public double getRandomNumber(Double min, Double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public Boolean getLucky(Double chance) {

        double randomNumber = getRandomNumber(0.0, 100.0);
        return randomNumber <= chance;
    }
}
