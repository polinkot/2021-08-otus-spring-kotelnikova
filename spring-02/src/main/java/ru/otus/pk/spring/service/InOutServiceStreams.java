package ru.otus.pk.spring.service;

import lombok.Getter;
import ru.otus.pk.spring.exception.WrongFormatException;

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

    public void printf(String line, Object ... args) {
        out.printf(line, args);
    }

    public String readLine() {
        return in.nextLine();
    }

    public int readInt(String prompt, String errMsg, int attemptsCount) {
        for (int i = 0; i < attemptsCount; i++) {
            println(prompt);
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            readLine();
            println(errMsg);
        }

        throw new WrongFormatException("Error!!! Incorrect format!!! Not an integer number.");
    }
}