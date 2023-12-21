package com.markovskisolutions.JJDT.config;



import com.markovskisolutions.JJDT.model.Role;
import com.markovskisolutions.JJDT.model.Roles;
import com.markovskisolutions.JJDT.model.UserEntity;
import com.markovskisolutions.JJDT.repository.RoleRepository;
import com.markovskisolutions.JJDT.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataBaseInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataBaseInit(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            UserEntity userEntity = new UserEntity();
            Role admin = new Role();
            admin.setRoleEnum(Roles.ADMIN);
            roleRepository.save(admin);
            Role userRole = new Role();
            userRole.setRoleEnum(Roles.USER);
            roleRepository.save(userRole);
            List<Role> allRoles = roleRepository.findAll();
            userEntity.setFirstName("Stilyan")
                    .setLastName("Petrov")
                    .setPhoneNumber("089xxxxxxx")
                    .setEmail("x.x.x.xxxx@gmail.com")
                    .setDateOfBirth(LocalDate.of(1990, 10, 3))
                    .setPassword(passwordEncoder.encode("testPassword"))
                    .setRoles(allRoles);
            userRepository.save(userEntity);
        }
    }
}
