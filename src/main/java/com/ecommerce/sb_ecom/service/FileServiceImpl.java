package com.ecommerce.sb_ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get the file name
        String originalFilename = file.getOriginalFilename();
        //Generate a unique filename using UUID
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path + File.separator + fileName;
        // check if path exist
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();
        //Prevents errors if the directory is missing by ensuring it exists before trying to save the file.
        //upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //return filename
        return fileName;
    }
}
