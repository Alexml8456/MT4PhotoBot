package com.alex.services;

import com.alex.utils.DateTime;
import com.alex.utils.SystemOperations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class ScreenshotCreator {

    public void FullScreenCapture() {
        try {
            Robot robot = new Robot();
            String folderName = SystemOperations.createDirectory();
            String fileName = folderName.concat(DateTime.ConvertTimeToString(DateTime.getGMTTimeMillis()).concat(".png"));
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            //Rectangle screenRect = new Rectangle(1, 108, 1900, 1040);
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, "png", new File(fileName));
            log.info("Screenshot created - {}", fileName);
        } catch (AWTException | IOException ex) {
            log.error("Cannot create screenshot ", ex);
        }
    }
}
