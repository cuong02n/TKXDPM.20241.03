package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmailAndActiveTrue(String email);
    boolean existsByEmail(String email);
}
