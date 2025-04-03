package com.whatsappclone.utils;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Slf4j
public class FileUtils {


    public FileUtils() {
    }

    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)){
            return new byte[0];
        }
        try {
            Path file=new File(fileUrl).toPath();
            return Files.readAllBytes(file);
        }catch (IOException e){
            log.warn("Not file found int the path {}",fileUrl);
        }
        return new byte[0];
    }
}
