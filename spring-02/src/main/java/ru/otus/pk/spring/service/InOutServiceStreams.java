package ru.otus.pk.spring.service;

import lombok.Getter;
import ru.otus.pk.spring.exception.AppException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Getter
public class InOutServiceStreams implements InOutService {
    private final Scanner in;
    private final PrintStream out;

    public InOutServiceStreams(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public void println(String line) {
        out.println(line);
    }

    public String nextLine() {
        return in.nextLine();
    }

    public int readInt() {
        for (int i = 0; i < 3; i++) {
            println("Please enter an integer number: ");
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            nextLine();
            println("Incorrect format.");
        }

        throw new AppException("Error!!! Incorrect format!!! Not an integer number.");
    }
}