package com.energize.shop.shopApp.repository;

import com.energize.shop.shopApp.enums.ERole;
import com.energize.shop.shopApp.repository.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
