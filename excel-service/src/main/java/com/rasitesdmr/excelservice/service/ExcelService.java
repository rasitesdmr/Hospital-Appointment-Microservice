package com.rasitesdmr.excelservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    void processSingleExcelFile(MultipartFile multipartFile);


}
