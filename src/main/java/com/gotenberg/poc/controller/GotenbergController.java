package com.gotenberg.poc.controller;

import com.gotenberg.poc.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class GotenbergController {

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/helloWorld")
    @ResponseBody
    public String getHelloWorld() {
        return "Hello World";
    }

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        uploadFileService.uploadFile(file);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(value = "/convertFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Map<String, String>> convertFile(@RequestPart(value = "file") MultipartFile file) {
      return  uploadFileService.convertFile(file);
    }

}
