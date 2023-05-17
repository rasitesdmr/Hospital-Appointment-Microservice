package com.rasitesdmr.hospitalservice.util;

import com.rasitesdmr.hospitalservice.repository.CityRepository;
import com.rasitesdmr.hospitalservice.repository.ClinicRepository;
import com.rasitesdmr.hospitalservice.repository.DoctorRepository;
import com.rasitesdmr.hospitalservice.repository.HospitalRepository;
import kafka.model.City;
import kafka.model.Clinic;
import kafka.model.Doctor;
import kafka.model.Hospital;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;
@Component
public class RandomIdGenerator implements IdentifierGenerator {

    private final Random random = new Random();

    private final CityRepository cityRepository;
    private final ClinicRepository clinicRepository;
    private final HospitalRepository hospitalRepository;

    public RandomIdGenerator(@Lazy CityRepository cityRepository, @Lazy ClinicRepository clinicRepository,@Lazy HospitalRepository hospitalRepository) {
        this.cityRepository = cityRepository;
        this.clinicRepository = clinicRepository;
        this.hospitalRepository = hospitalRepository;

    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        if (o instanceof City city) {
            if (city.getId() != null) {
                return city.getId();
            }else {
                // TODO eğer db'de veri yoksa ilk manuel eklemede patlar.
               Long id = cityRepository.findTopByOrderByIdDesc().getId();
               return id +1;
            }
        }
        if (o instanceof Clinic clinic){
            if(clinic.getId()!= null){
                return clinic.getId();
            }else {
                Long id = clinicRepository.findTopByOrderByIdDesc().getId();
                return id +1;
            }
        }

        if (o instanceof Hospital hospital){
            if(hospital.getId()!= null){
                return hospital.getId();
            }else {
                Long id = hospitalRepository.findTopByOrderByIdDesc().getId();
                return id +1;
            }
        }
        if (o instanceof Doctor doctor){
            if (doctor.getIdentityNumber()!=null){
                return doctor.getIdentityNumber();
            }
        }

        return null;

    }


}
