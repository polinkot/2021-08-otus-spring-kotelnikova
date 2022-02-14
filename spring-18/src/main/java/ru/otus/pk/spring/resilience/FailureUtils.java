package ru.otus.pk.spring.resilience;

import java.util.Random;

public class FailureUtils {

    public static void failureForDemo(String method) {
        if (new Random().nextBoolean()) {
            System.out.println(method + " success");
            return;
        }

        if (new Random().nextBoolean()) {
            System.out.println(method + " Exception for Demo");
            throw new IllegalStateException(method + " IllegalStateException for Demo");
        }

        try {
            System.out.println(method + " delayed for Demo");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
