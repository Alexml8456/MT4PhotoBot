package com.alex;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        File dir = new File("/home/alexml/Downloads/IDEA_Projects/Mt4PhotoBot/2019-03-31");
        File[] files = dir.listFiles();
        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        System.out.println(files[0].getPath());
    }
}