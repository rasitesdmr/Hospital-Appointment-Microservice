package com.rasitesdmr.excelservice.mapper;

import com.rasitesdmr.excelservice.exception.RegistrationException;
import kafka.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class FileMapperImpl implements FileMapper {

    @Override
    public FileInfo convertMultipartFileToFileInfo(MultipartFile multipartFile) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName(); // TODO Burada metot adını almak için yeni bir nesne oluşturuluyor bu pek mantıklı değil gibi

        if (multipartFile == null) {
            log.error("[Metot : {}] - Dosya null olarak geldi", methodName);
            return null;
        }

        FileInfo fileInfo = new FileInfo();

        try {
            fileInfo.setName(multipartFile.getOriginalFilename());
            fileInfo.setType(multipartFile.getContentType());
            fileInfo.setSize(multipartFile.getSize());
            fileInfo.setInputStream(multipartFile.getInputStream());
            fileInfo.setData(multipartFile.getBytes());
        } catch (IOException exception) {
            log.error("[Metot : {}] - multiparFile sınıfını fileInfo sınıfına set'lerken hata oluştu : {}", methodName, exception.getMessage());
            throw new RegistrationException(exception.getMessage());
        }
        return fileInfo;
    }
}
