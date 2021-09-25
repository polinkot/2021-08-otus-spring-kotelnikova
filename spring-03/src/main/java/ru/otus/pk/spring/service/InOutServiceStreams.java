package ru.otus.pk.spring.service;

import lombok.Getter;
import org.springframework.context.support.MessageSourceAccessor;
import ru.otus.pk.spring.exception.AppException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Getter
public class InOutServiceStreams implements InOutService {
    private final Scanner in;
    private final PrintStream out;
    private final MessageSourceAccessor messageSourceAccessor;

    public InOutServiceStreams(InputStream in, PrintStream out, MessageSourceAccessor messageSourceAccessor) {
        this.in = new Scanner(in);
        this.out = out;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    public void println(String line) {
        out.println(line);
    }

    public String nextLine() {
        return in.nextLine();
    }

    public int readInt() {
        for (int i = 0; i < 3; i++) {
            println(getMessage("quiz.enter-integer"));
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            nextLine();
            println(getMessage("quiz.incorrect-format"));
        }

        throw new AppException(getMessage("quiz.error.incorrect-format"));
    }

    private String getMessage(String key) {
        return messageSourceAccessor.getMessage(key);
    }
}