package com.todoList.app.infrastructure.helper;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.todoList.app.application.port.out.helper.MessageHelper;

@Component
public class MessageHelperImpl implements MessageHelper {
    private final MessageSource messageSource;

    public MessageHelperImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key) {
        return messageSource.getMessage(key, null, Locale.getDefault());
    }
}