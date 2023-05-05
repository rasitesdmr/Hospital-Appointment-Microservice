package com.rasitesdmr.excelservice.mapper;

import kafka.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileMapper {

    FileInfo convertMultipartFileToFileInfo(MultipartFile multipartFile);
}
