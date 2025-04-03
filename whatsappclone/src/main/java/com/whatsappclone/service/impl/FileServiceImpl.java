package com.whatsappclone.service.impl;

import com.whatsappclone.service.FileService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${application.file.uploads.media-output-path}")
    private String fileUploadPath;


    @Override
    public String saveFile(@NotNull MultipartFile sourceFile,
                           @NonNull String senderId) {
        String fileUploadSubPath="users"+ separator+senderId;
        return uploadFile(sourceFile,fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile sourceFile,
                              @NonNull String fileUploadSubPath) {
        String finalUploadPath=fileUploadPath + separator+ sourceFile.getOriginalFilename();
        File targetFolder=new File(fileUploadSubPath);

        if (!targetFolder.exists()){
            boolean folderCreated=targetFolder.mkdirs();
            if (!folderCreated){
                log.warn("Failed to created the target folder");
                return  null;
            }
        }
        String fileExtension=getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath=fileUploadPath+ separator + currentTimeMillis() + fileExtension;
        Path targetPath= Paths.get(targetFilePath);
        try {
            Files.write(targetPath,sourceFile.getBytes());
            log.info("File saved to {}",targetPath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved: {}",e.getMessage());
        }
        return null;
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()){
            return "";
        }

        int lastDotIndex=filename.lastIndexOf(".");
        if(lastDotIndex == -1){
            return "";
        }
        return filename.substring(lastDotIndex+1).toLowerCase();
    }
}
