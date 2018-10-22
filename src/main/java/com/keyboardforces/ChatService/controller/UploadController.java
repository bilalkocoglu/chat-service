package com.keyboardforces.ChatService.controller;

import com.keyboardforces.ChatService.dto.ChatResponseDTO;
import com.keyboardforces.ChatService.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = UploadController.END_POINT)
public class UploadController {
    static final String END_POINT = "/upload";

    static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    StorageService storageService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    List<String> files = new ArrayList<String>();

    @CrossOrigin
    @PostMapping(value = "/post")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());

//GÖNDERİLİYOR FAKAT UĞRAŞMAK GEREK
            ChatResponseDTO chatResponseDTO = new ChatResponseDTO();

            LocalDateTime now = LocalDateTime.now();

            now = now.plusHours(3);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");

            String nowString = now.format(formatter);

            chatResponseDTO.setDate(nowString);

            String fileURL = MvcUriComponentsBuilder
                    .fromMethodName(UploadController.class, "getFile", file.getOriginalFilename()).build().toString();
            LOGGER.info(fileURL);
            chatResponseDTO.setMessage(fileURL);
            chatResponseDTO.setInfo("kişi bilgisi");

            simpMessagingTemplate.convertAndSend("/chat",chatResponseDTO);

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @CrossOrigin
    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {
        files.add("UMLPro.zip");
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(UploadController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
