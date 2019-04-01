package com.alex.services;

import com.alex.telegram.TelegramPhotoBot;
import com.alex.utils.SystemOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Slf4j
//@Profile("scheduled")
public class Scheduling {

    @Value("${send.photo.message.enable}")
    private boolean enable;

    @Autowired
    private DataHolder dataHolder;

    @Autowired
    private TelegramPhotoBot telegramPhotoBot;

    @Autowired
    private ScreenshotCreator screenshotCreator;

    @Scheduled(cron = "0 0/1 * ? * *")
    public void test() {
        screenshotCreator.FullScreenCapture();

        if (enable && !dataHolder.getSubscriptions().isEmpty()) {
            telegramPhotoBot.pushPhotoMessage(dataHolder.getSubscriptions(), SystemOperations.findLastFile());
        }
    }
}
