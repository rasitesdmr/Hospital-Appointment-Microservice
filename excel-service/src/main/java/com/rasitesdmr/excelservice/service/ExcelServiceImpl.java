package com.rasitesdmr.excelservice.service;

import com.rasitesdmr.excelservice.mapper.FileMapper;
import com.rasitesdmr.excelservice.producer.KafkaProducer;
import com.rasitesdmr.excelservice.utils.ExcelUtils;
import kafka.model.FileInfo;
import kafka.model.dto.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService {


    private final FileMapper fileMapper;
    private final KafkaProducer kafkaProducer;

    public ExcelServiceImpl(FileMapper fileMapper,KafkaProducer kafkaProducer) {
        this.fileMapper = fileMapper;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void processSingleExcelFile(MultipartFile multipartFile) {
        FileInfo fileInfo = fileMapper.convertMultipartFileToFileInfo(multipartFile);
        if (ExcelUtils.hasExcelFormat(fileInfo)) {
            InputStream inputStream = fileInfo.getInputStream();
            processFileByName(inputStream, fileInfo.getName());
        }
    }

    public void processFileByName(InputStream inputStream, String fileName) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        Map<Integer, String> setterMapping = new HashMap<>();

        switch (fileName) {
            case "city.xlsx":
                setterMapping.put(0, "setId");
                setterMapping.put(1, "setName");
                List<CityResponse> cityList = ExcelUtils.connectExcelColumnsToModelFields(inputStream, CityResponse.class,setterMapping);
                kafkaProducer.sendCityListToQueue(cityList);
                break;
            case "clinic.xlsx":
                setterMapping.put(0,"setId");
                setterMapping.put(1,"setName");
                List<ClinicResponse> clinicList = ExcelUtils.connectExcelColumnsToModelFields(inputStream, ClinicResponse.class,setterMapping);
                kafkaProducer.sendClinicListToQueue(clinicList);
                break;
            case "hospital.xlsx":
                setterMapping.put(0,"setId");
                setterMapping.put(1,"setName");
                setterMapping.put(2,"setAddress");
                setterMapping.put(3,"setCityId");
                List<HospitalResponse> hospitalList = ExcelUtils.connectExcelColumnsToModelFields(inputStream,HospitalResponse.class,setterMapping);
                kafkaProducer.sendHospitalListToQueue(hospitalList);
                break;
            case "doctor.xlsx":
                setterMapping.put(0,"setIdentityNumber");
                setterMapping.put(1,"setFirstName");
                setterMapping.put(2,"setLastName");
                setterMapping.put(3,"setDateOfBirth");
                setterMapping.put(4,"setPhoneNumber");
                setterMapping.put(5,"setEmail");
                setterMapping.put(6,"setProfession");
                List<DoctorResponse>doctorList = ExcelUtils.connectExcelColumnsToModelFields(inputStream, DoctorResponse.class,setterMapping);
                kafkaProducer.sendDoctorListToQueue(doctorList);
                break;
            case "doctorClinic.xlsx":
                setterMapping.put(0,"setDoctorId");
                setterMapping.put(1,"setClinicId");
                List<DoctorClinicResponse> doctorClinicList = ExcelUtils.connectExcelColumnsToModelFields(inputStream,DoctorClinicResponse.class,setterMapping);
                kafkaProducer.sendDoctorClinicListToQueue(doctorClinicList);
                break;
            case "doctorHospital.xlsx":
                setterMapping.put(0,"setDoctorId");
                setterMapping.put(1,"setHospitalId");
                List<DoctorHospitalResponse> doctorHospitalList = ExcelUtils.connectExcelColumnsToModelFields(inputStream,DoctorHospitalResponse.class,setterMapping);
                kafkaProducer.sendDoctorHospitalListToQueue(doctorHospitalList);
                break;
            case "hospitalClinic.xlsx":
                setterMapping.put(0,"setHospitalId");
                setterMapping.put(1,"setClinicId");
                List<HospitalClinicResponse> hospitalClinicList = ExcelUtils.connectExcelColumnsToModelFields(inputStream,HospitalClinicResponse.class,setterMapping);
                kafkaProducer.sendHospitalClinicListToQueue(hospitalClinicList);
                break;
            case "doctorPhoneNumber.xlsx":
                setterMapping.put(0,"setIdentityNumber");
                setterMapping.put(1,"setFirstName");
                setterMapping.put(2,"setLastName");
                setterMapping.put(3,"setDateOfBirth");
                setterMapping.put(4,"setPhoneNumber");
                setterMapping.put(5,"setEmail");
                setterMapping.put(6,"setProfession");
                List<DoctorResponse> doctorPhoneNumber = ExcelUtils.connectExcelColumnsToModelFields(inputStream, DoctorResponse.class,setterMapping);
                kafkaProducer.sendDoctorPhoneNumberListToQueue(doctorPhoneNumber);
            default:
                log.error("[Metot - {}] - {} adına sahip dosya işlenmedi",methodName,fileName);
        }

    }




}
