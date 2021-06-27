package com.energize.shop.shopApp;

import com.energize.shop.shopApp.enums.ERole;
import com.energize.shop.shopApp.repository.RoleRepository;
import com.energize.shop.shopApp.repository.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class ShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopAppApplication.class, args);
	}

	@Component
	class DemoCommandLineRunner implements CommandLineRunner {
		@Autowired
		private RoleRepository roleRepository;

		@Override
		public void run(String... args) throws Exception {
			Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
			if (!userRole.isPresent() )
			{
			Role role = new Role();
			role.setName(ERole.ROLE_USER);
			roleRepository.save(role);
			}
			Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
			if (!adminRole.isPresent() )
			{
				Role role = new Role();
				role.setName(ERole.ROLE_ADMIN);
				roleRepository.save(role);
			}
		}
	}

}
