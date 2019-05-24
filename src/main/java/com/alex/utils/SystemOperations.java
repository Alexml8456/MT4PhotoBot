package com.alex.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.Arrays;

@Slf4j
public class SystemOperations {

    private static final String PATH = ".".concat(File.separator);
    private static final String FOLDER = DateTime.getCurrentDate(DateTime.getGMTTimeMillis());
    private static final String DIRECTORY_NAME = PATH.concat(FOLDER);

    public static String createDirectory() {
        String path = ".".concat(File.separator);
        String folder = DateTime.getCurrentDate(DateTime.getGMTTimeMillis());
        String directoryName = path.concat(folder);
        File directory = new File(directoryName);
        boolean isDirCreated = directory.mkdir();
        if (isDirCreated) {
            log.info("New folder created - {}", directory.getName());
        }
        return directory.getPath().concat(File.separator);
    }

    public static String findLastFile() {
        File dir = new File(DIRECTORY_NAME);
        File[] files = dir.listFiles();
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        return files[0].getPath();
    }
}