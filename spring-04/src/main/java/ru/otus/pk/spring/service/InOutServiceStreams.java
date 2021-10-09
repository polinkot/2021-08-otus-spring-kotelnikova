package ru.otus.pk.spring.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.exception.WrongFormatException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

@Getter
@Service
public class InOutServiceStreams implements InOutService {
    private final Scanner in;
    private final PrintStream out;

    public InOutServiceStreams(@Value("#{ T(java.lang.System).in}") InputStream in,
                               @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public void println(String line) {
        out.println(line);
    }

    public String readLine() {
        return in.nextLine();
    }

    public int readInt(String prompt, String errMsg, int attemptsCount) {
        for (int i = 0; i < attemptsCount; i++) {
            println(prompt);

            try {
                return in.nextInt();
            } catch (InputMismatchException e) {
                readLine();
                println(errMsg);
            }
        }

        throw new WrongFormatException("Error!!! Incorrect format. Not an integer.");
    }
}