package com.rasitesdmr.hospitalservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.hospitalservice.exception.AlreadyAvailableException;
import com.rasitesdmr.hospitalservice.exception.BadRequestException;
import com.rasitesdmr.hospitalservice.exception.NotAvailableException;
import com.rasitesdmr.hospitalservice.exception.RegistrationException;
import com.rasitesdmr.hospitalservice.repository.CityRepository;
import com.rasitesdmr.hospitalservice.repository.HospitalRepository;
import kafka.model.City;
import kafka.model.Hospital;
import kafka.model.dto.request.HospitalRequest;
import kafka.model.dto.response.HospitalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final CityRepository cityRepository;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, CityRepository cityRepository) {
        this.hospitalRepository = hospitalRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public HospitalResponse createHospital(HospitalRequest hospitalRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        int countAllCitys = cityRepository.findAll().size();
        if (countAllCitys == 0){
            log.error("[Metot : {}] - Hastane eklerken , şehir tablosunda en az 1 tane veri olmalıdır",methodName);
            throw new BadRequestException("Hastane eklerken , şehir tablosunda en az 1 tane veri olmalıdır");
        }

        String uppercaseHospitalName = hospitalRequest.getName().toUpperCase();

        boolean hospitalExists = hospitalRepository.existsByName(uppercaseHospitalName);
        if (hospitalExists){
            log.error("[Metot : {}] - {} adına sahip hastane zaten mevcut", methodName,hospitalRequest.getName());
            throw  new AlreadyAvailableException("Hastane zaten mevcut : " + hospitalRequest.getName());
        }

        boolean cityExists = cityRepository.existsById(hospitalRequest.getCityId());
        if (!cityExists){
            log.error("[Metot : {}] - {} id numarasına sahip şehir bulunamadı", methodName,hospitalRequest.getCityId());
            throw  new NotAvailableException("Şehir bulunamadı : " + hospitalRequest.getCityId());
        }

        Hospital hospital = new Hospital();
        try {
            hospital.setName(uppercaseHospitalName);
            hospital.setAddress(hospitalRequest.getAddress().toUpperCase());
            hospital.setCity(cityRepository.findById(hospitalRequest.getCityId()).get());
            hospitalRepository.save(hospital);
        }catch (Exception exception){
            log.error("[Metot : - {}] - {} adına sahip hastane varlığı kaydedilirken hata oluştu : {}",methodName,uppercaseHospitalName,exception.getMessage());
            throw new RegistrationException(uppercaseHospitalName + " adına sahip hastane varlığı kaydedilirken hata oluştu : " + exception.getMessage());
        }

        return HospitalResponse.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .address(hospital.getAddress())
                .cityId(hospital.getCity().getId())
                .build();
    }

    @Override
    public void createExcelHospital(List<HospitalResponse> hospitalResponseList) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        ObjectMapper mapper = new ObjectMapper();
        List<HospitalResponse> hospitalList = mapper.convertValue(hospitalResponseList, new TypeReference<List<HospitalResponse>>() {});

        for (HospitalResponse hospitalResponse: hospitalList){

            String uppercaseHospitalName = hospitalResponse.getName().toUpperCase();

            boolean hospitalExists = hospitalRepository.existsByName(uppercaseHospitalName);
            if (!hospitalExists){
                boolean cityExists = cityRepository.existsById(hospitalResponse.getCityId());
                if (cityExists){
                    Hospital hospital = new Hospital();
                    try {
                        hospital.setId(hospitalResponse.getId());
                        hospital.setName(uppercaseHospitalName);
                        hospital.setAddress(hospitalResponse.getAddress().toUpperCase());
                        hospital.setCity(cityRepository.findById(hospitalResponse.getCityId()).get());
                        hospitalRepository.save(hospital);
                        log.info("[Metot : {}] - {} numaralı id'ye sahip hastane varlığı kaydedildi",methodName,hospital.getId());
                    }catch (Exception exception){
                        log.error("[Metot : {}] - {} adına sahip hastane varlığı kaydedilirken hata oluştu : {}",methodName,uppercaseHospitalName,exception.getMessage());
                    }
                }else {
                    log.error("[Metot : {}] - {} numaralı id'ye sahip şehir bulunamadı", methodName,hospitalResponse.getCityId());
                }
            }else {
                log.error("[Metot : {}] - {} adına sahip hastane zaten mevcut", methodName,uppercaseHospitalName);
            }


        }
    }

    @Override
    public List<HospitalResponse> getHospitalListByCityName(String cityName) {
        City city = cityRepository.findByName(cityName);
        List<Hospital> hospitalList = city.getHospitals();
        Set<Hospital> hospitalSet = new HashSet<>(hospitalList);
        List<Hospital> filteredHospitalList = new ArrayList<>(hospitalSet);


        return null;
    }
}
