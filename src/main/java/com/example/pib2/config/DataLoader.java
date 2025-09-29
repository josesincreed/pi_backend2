package com.example.pib2.config;

import com.example.pib2.model.user.User;
import com.example.pib2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario ADMIN si no existe
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);

            userRepository.save(admin);
            System.out.println("Usuario ADMIN creado: username=admin, password=admin123");
        }

        // Crear usuario USER si no existe
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            userRepository.save(user);
            System.out.println("Usuario USER creado: username=user, password=user123");
        }

        // Crear otro usuario USER de ejemplo
        if (userRepository.findByUsername("john").isEmpty()) {
            User john = new User();
            john.setUsername("john");
            john.setEmail("john@example.com");
            john.setPassword(passwordEncoder.encode("john123"));
            john.setRole("USER");
            john.setEnabled(true);
            john.setAccountNonExpired(true);
            john.setAccountNonLocked(true);
            john.setCredentialsNonExpired(true);

            userRepository.save(john);
            System.out.println("Usuario USER creado: username=john, password=john123");
        }

        System.out.println("\n=== CREDENCIALES DE PRUEBA DISPONIBLES ===");
        System.out.println("ADMIN: username=admin | password=admin123");
        System.out.println("USER:  username=user  | password=user123");
        System.out.println("USER:  username=john  | password=john123");
        System.out.println("==========================================\n");
    }
}
