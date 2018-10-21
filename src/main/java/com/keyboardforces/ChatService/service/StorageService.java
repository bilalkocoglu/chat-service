package com.keyboardforces.ChatService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class StorageService {
    static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);
    private final Path rootLocation = Paths.get("upload-dir");

    public void store(MultipartFile file){
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));

            try {
                StringBuilder sb = new StringBuilder();
                sb.append("Test String\nasdfasdfasf\nasdfasdfasf\n");

                File f = new File(String.valueOf(this.rootLocation.resolve("UMLPro.zip")));
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
                ZipEntry e = new ZipEntry("mytext.txt");
                out.putNextEntry(e);

                byte[] data = sb.toString().getBytes();
                out.write(data, 0, data.length);
                out.closeEntry();

                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll(){
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

}
