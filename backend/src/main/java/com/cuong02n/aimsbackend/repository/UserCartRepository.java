package com.cuong02n.aimsbackend.repository;

import com.cuong02n.aimsbackend.model.entity.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCartRepository extends JpaRepository<UserCart, String> {

}
