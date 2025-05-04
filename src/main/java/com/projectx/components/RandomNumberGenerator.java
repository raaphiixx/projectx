package com.projectx.components;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {

    private final Random random;

    public RandomNumberGenerator() {
        this.random = new Random();
    }

    public int getRandomNumber(int max) {
        return random.nextInt(1, max + 1);
    }
}
