package ru.otus.pk.spring.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.otus.pk.spring.exception.WrongFormatException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static ru.otus.pk.spring.config.MessageSourceConfig.QUIZ_ERROR_INCORRECT_FORMAT;

@Getter
@Service
public class InOutServiceStreams implements InOutService {
    private final Scanner in;
    private final PrintStream out;
    private final MessageSourceAccessor messageSourceAccessor;

    public InOutServiceStreams(@Value("#{ T(java.lang.System).in}") InputStream in,
                               @Value("#{ T(java.lang.System).out}") PrintStream out,
                               MessageSourceAccessor messageSourceAccessor) {
        this.in = new Scanner(in);
        this.out = out;
        this.messageSourceAccessor = messageSourceAccessor;
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
            if (in.hasNextInt()) {
                return in.nextInt();
            }

            readLine();
            println(errMsg);
        }

        throw new WrongFormatException(messageSourceAccessor.getMessage(QUIZ_ERROR_INCORRECT_FORMAT));
    }
}