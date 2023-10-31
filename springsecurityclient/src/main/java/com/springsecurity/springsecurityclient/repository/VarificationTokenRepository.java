package com.springsecurity.springsecurityclient.repository;

import com.springsecurity.springsecurityclient.entity.VarificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VarificationTokenRepository extends JpaRepository<VarificationToken,Long> {
    VarificationToken findByToken(String token);
}
