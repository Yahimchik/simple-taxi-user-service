package com.taxisimpledrive.taxiuserservice.util;

import com.taxisimpledrive.taxiuserservice.model.Role;
import com.taxisimpledrive.taxiuserservice.repository.RoleRepository;
import com.taxisimpledrive.taxiuserservice.util.generator.UUIDGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UUIDGenerator uuidGenerator;

    @Override
    public void run(String... args) {
        String[] roles = {"USER", "DRIVER", "ADMIN", "SUPPORT"};

        for (String roleName : roles) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> insertIfNotExist(roleName));
        }

        System.out.println("Roles initialized!");
    }

    private Role insertIfNotExist(String roleName) {
        Role role = new Role();
        role.setId(uuidGenerator.generate());
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
