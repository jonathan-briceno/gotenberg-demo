package com.gotenberg.poc.service;

import dev.inaka.Jotenberg;
import dev.inaka.libreoffice.LibreOfficeOptions;
import dev.inaka.libreoffice.LibreOfficePageProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.commons


@Service
public class UploadFileService {

    @Autowired
    private Jotenberg jotenberg;


    public Boolean uploadFile(MultipartFile multipartFile) {
        try {
            if(multipartFile.isEmpty()) {
                throw new Exception("Yuk");
            }

            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src/main/resources/targetFile.docx");

            try (OutputStream outStream = new FileOutputStream(targetFile)) {
                outStream.write(buffer);
            }

            List<File> files = new ArrayList<>();

            files.add(targetFile);

            LibreOfficePageProperties pageProperties = new LibreOfficePageProperties.Builder().build();
            LibreOfficeOptions libreOfficeOptions = new LibreOfficeOptions.Builder().build();

            System.out.println("aaaa" + jotenberg.getClient());

            CloseableHttpResponse response = jotenberg.convertWithLibreOffice(files, pageProperties, libreOfficeOptions);

           System.out.println("response " + response);

           InputStream pdfContent = response.getEntity().getContent();
          //  FileUtils.copyInputStreamToFile(pdfContent, tempFile);
            File pdfTargetFile = new File("src/main/resources/targetFile.pdf");

            // creating a phisical copy of the pdf
            java.nio.file.Files.copy(
                    pdfContent,
                    pdfTargetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            IOUtils.closeQuietly(pdfContent);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    public ResponseEntity<Map<String, String>> convertFile(MultipartFile multipartFile) {
        try {
            if(multipartFile.isEmpty()) {
                throw new Exception("Yuk");
            }

            InputStream initialStream = multipartFile.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src/main/resources/targetFile.docx");

            try (OutputStream outStream = new FileOutputStream(targetFile)) {
                outStream.write(buffer);
            }

            List<File> files = new ArrayList<>();

            files.add(targetFile);

            LibreOfficePageProperties pageProperties = new LibreOfficePageProperties.Builder().build();
            LibreOfficeOptions libreOfficeOptions = new LibreOfficeOptions.Builder().build();

            System.out.println("aaaa" + jotenberg.getClient());

            CloseableHttpResponse response = jotenberg.convertWithLibreOffice(files, pageProperties, libreOfficeOptions);

            System.out.println("response " + response);

            InputStream pdfContent = response.getEntity().getContent();
            //  FileUtils.copyInputStreamToFile(pdfContent, tempFile);
            File pdfTargetFile = new File("src/main/resources/targetFile.pdf");
            // Path pdfTargetPath = pdfTargetFile.toPath();
            // creating a phisical copy of the pdf
            java.nio.file.Files.copy(
                    pdfContent,
                    pdfTargetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            IOUtils.closeQuietly(pdfContent);

            byte[] pdfBytes = Files.readAllBytes(pdfTargetFile.toPath());

            String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);


            Map<String, String> convertResponse = new HashMap<>();
            convertResponse.put("base64", base64Pdf);
            convertResponse.put("contentType", "application/pdf");
            return ResponseEntity.ok(convertResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
