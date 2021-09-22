package ru.otus.pk.spring.service;

import lombok.Getter;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Getter
public class InOutServiceImpl implements InOutService {
    private final Scanner in;
    private final PrintStream out;

    public InOutServiceImpl(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public void println(String line) {
        out.println(line);
    }

    public String nextLine() {
        return in.nextLine();
    }

    public boolean hasNextInt() {
        return in.hasNextInt();
    }

    public int nextInt() {
        return in.nextInt();
    }
}