package com.rasitesdmr.securityservice.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Random;

public class RandomIdGenerator implements IdentifierGenerator {

    private final Random random = new Random();

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        long randomId = random.nextLong();
        return Math.abs(randomId) ;
    }
}
