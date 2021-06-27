package com.energize.shop.shopApp.repository;

import com.energize.shop.shopApp.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository  extends JpaRepository<User,Long> {
 Boolean existsByUsername(String username);
 Boolean existsByEmail(String email);
   Optional< User> findByUsername(String userName);
}
