package com.alex.telegram;

import com.alex.services.DataHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TelegramPhotoBot extends TelegramLongPollingBot {


    private class ImageMessage {
        String numbers = null;
        String caption = null;
        String image = null;
    }

    private static final String INSTANCE_ID = "YOUR_INSTANCE_ID_HERE";
    private static final String CLIENT_ID = "311771410";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET_HERE";

    private static final String GATEWAY_URL = "https://api.telegram.org/bot705468703:AAFypv2QYdCHh5eSQR8UM-PgO1LDK6czWSk";

    private String botToken;

    @Autowired
    private DataHolder dataHolder;

    @Override
    public void onUpdateReceived(Update update) {
        handle(update);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(this::handle);
    }

    private void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            log.info("User message - " + text + "; " + "User ID - " + chatId);
            if (text.contains("MT4")) {
                dataHolder.addSubscriber(chatId);
                pushMessage(Collections.singletonList(chatId), "You verified MT4Photo subscription");
            } else if (text.contains("stop")) {
                dataHolder.deleteSubscriber(chatId);
            }
        }
    }


    public void pushMessage(List<String> chatIds, String text) {
        for (String chatId : chatIds) {
            SendMessage sendMessageRequest = new SendMessage();
            sendMessageRequest.setChatId(chatId);
            sendMessageRequest.setText(text);
            try {
                execute(sendMessageRequest);
            } catch (TelegramApiException e) {
                log.error("Can't send telegram message", e);
            }
        }
    }

    public void pushPhotoMessage(List<String> chatIds, String filePath) {
        for (String chatId : chatIds) {
            SendPhoto sendPhotoRequest = new SendPhoto();
            sendPhotoRequest.setChatId(chatId);
            sendPhotoRequest.setPhoto(new File(filePath));
            sendPhotoRequest.setCaption("Photo");
            try {
                execute(sendPhotoRequest);
            } catch (TelegramApiException e) {
                log.error("Can't send telegram photo message", e);
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "MT4PhotoBot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public void onClosing() {
        log.warn("Exit telegram bot");
    }

    @PostConstruct
    public void init() {
        log.info("Telegram bot is up and running with token: " + botToken);
    }

}
