package com.rasitesdmr.securityservice.repository;

import kafka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User getByIdentityNumber(String identityNumber);

    boolean existsByEmail(String email);
}
