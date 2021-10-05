package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageFacadeImpl implements MessageFacade {
    private final InOutService inOutService;
    private final MessageService messageService;

    @Override
    public void println(String line) {
        inOutService.println(line);
    }

    @Override
    public String readLine() {
        return inOutService.readLine();
    }

    @Override
    public int readInt(String prompt, String errMsg, int attemptsCount) {
        return inOutService.readInt(prompt, errMsg, attemptsCount);
    }

    @Override
    public String getMessage(String key) {
        return messageService.getMessage(key);
    }

    @Override
    public String getMessage(String key, Object[] objects) {
        return messageService.getMessage(key, objects);
    }

    @Override
    public void printlnLocalized(String key) {
        inOutService.println(messageService.getMessage(key));
    }

    @Override
    public void printlnLocalized(String key, Object[] objects) {
        inOutService.println(messageService.getMessage(key, objects));
    }

    @Override
    public int readIntLocalized(String promptKey, String errMsgKey, int attemptsCount) {
        return inOutService.readInt(messageService.getMessage(promptKey), messageService.getMessage(errMsgKey), attemptsCount);
    }
}