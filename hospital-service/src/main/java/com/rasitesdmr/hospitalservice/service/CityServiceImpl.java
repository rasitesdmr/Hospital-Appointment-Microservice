package com.rasitesdmr.hospitalservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasitesdmr.hospitalservice.exception.AlreadyAvailableException;
import com.rasitesdmr.hospitalservice.exception.BadRequestException;
import com.rasitesdmr.hospitalservice.exception.RegistrationException;
import com.rasitesdmr.hospitalservice.repository.CityRepository;
import kafka.model.City;
import kafka.model.dto.request.CityRequest;
import kafka.model.dto.response.CityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest cityRequest) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        String cityName = cityRequest.getName();
        if (cityName == null || cityName.trim().isEmpty()) {
            log.error("[Metot : {}] - Şehir adı boş olmamalı",methodName);
            throw new BadRequestException("Şehir adı boş olmamalı");
        }

        String uppercaseCityName = cityName.toUpperCase();

        boolean cityExists = cityRepository.existsByName(uppercaseCityName);
        if (cityExists){
            log.error("[Metot : {}] - {} adına sahip şehir zaten mevcut",methodName,uppercaseCityName );
        throw  new AlreadyAvailableException("Şehir zaten mevcut : " + uppercaseCityName );
        }

        City city = new City();
        try {
            city.setName(uppercaseCityName);
            cityRepository.save(city);
            log.info("[Metot : {}] - {} numaralı id'ye sahip şehir varlığı kaydedildi" ,methodName,city.getId());
        }catch (Exception exception){
            log.error("[Metot : - {}] - {} adına sahip şehir varlığını kaydederken hata oluştu : {}",methodName,uppercaseCityName,exception.getMessage());
            throw new RegistrationException(uppercaseCityName + "adına sahip şehir varlığını kaydederken hata oluştu");
        }

        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }

    @Override
    public void createExcelCity(List<CityResponse> cityResponseList) {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

        ObjectMapper mapper = new ObjectMapper();
        List<CityResponse> cityList = mapper.convertValue(cityResponseList, new TypeReference<List<CityResponse>>() {});

        for(CityResponse cityResponse : cityList){

            String uppercaseCityName = cityResponse.getName().toUpperCase();
            boolean cityExists = cityRepository.existsByName(cityResponse.getName().toUpperCase());
            if (cityExists){
                log.error("[Metot : {}] - {} adına sahip şehir zaten mevcut",methodName,uppercaseCityName);
            }else {
                City city = new City();
                try {
                    city.setId(cityResponse.getId());
                    city.setName(uppercaseCityName);
                    cityRepository.save(city);
                    log.info("[Metot : {}] - {} numaralı id'ye sahip şehir varlığı kaydedildi" ,methodName,cityResponse.getId());
                }catch (Exception exception){
                    log.error("[Metot : - {}] - {} adına sahip şehir varlığını kaydederken hata oluştu : {}",methodName,uppercaseCityName,exception.getMessage());
                }
            }



        }
    }
}
