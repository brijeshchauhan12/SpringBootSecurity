package com.springAuth.AuthServer.oathserver.repository;


import com.springAuth.AuthServer.oathserver.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

       //User findByEmail(String email);
}