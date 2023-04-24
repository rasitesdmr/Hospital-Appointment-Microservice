package com.rasitesdmr.securityservice.repository;

import com.rasitesdmr.securityservice.enums.ERole;
import kafka.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(ERole role);
}
