package com.alex.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FolderCreator {

    public static String createDirectory() {
        String PATH = ".".concat(File.separator);
        String directoryName = PATH.concat(DateTime.getCurrentDate(DateTime.getGMTTimeMillis()));
        File directory = new File(directoryName);
        boolean isDirCreated = directory.mkdir();
        if (isDirCreated) {
            log.info("New folder created - {}",directory.getName());
        }
        return directory.getPath().concat(File.separator);
    }
}