package com.rasitesdmr.excelservice.controller;

import com.rasitesdmr.excelservice.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @PostMapping(path = "/processSingleExcelFile", consumes = {"multipart/form-data"})
    public ResponseEntity<String> processSingleExcelFile(@RequestPart(name = "file") MultipartFile file) {
        excelService.processSingleExcelFile(file);
        return new ResponseEntity<>("Modeller kuyruklara gönderildi", HttpStatus.OK);
    }
}
