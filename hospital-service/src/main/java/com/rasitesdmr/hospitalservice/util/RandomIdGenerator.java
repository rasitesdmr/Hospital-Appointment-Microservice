package com.rasitesdmr.hospitalservice.util;

import kafka.model.City;
import kafka.model.Clinic;
import kafka.model.Doctor;
import kafka.model.Hospital;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Random;

public class RandomIdGenerator implements IdentifierGenerator {

    private final Random random = new Random();

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        if (o instanceof City city) {
            if (city.getId() != null) {
                return city.getId();
            }
        }
        if (o instanceof Clinic clinic){
            if(clinic.getId()!= null){
                return clinic.getId();
            }
        }

        if (o instanceof Hospital hospital){
            if(hospital.getId()!= null){
                return hospital.getId();
            }
        }
        long randomId = random.nextLong();
        return Math.abs(randomId);
    }
}
