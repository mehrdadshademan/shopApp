package com.energize.shop.shopApp.repository;

import com.energize.shop.shopApp.repository.model.CommandModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<CommandModel,Long> {


}
