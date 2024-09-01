package com.fun.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fun.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
   public User findByEmail(String email);
   
   @Query("SELECT DISTINCT u from User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
   public List<User> searchUser(@Param("query") String query);
}
