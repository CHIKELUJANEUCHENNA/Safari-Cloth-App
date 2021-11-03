package com.example.safariwebstore008;


import com.cloudinary.*;
import com.example.safariwebstore008.enums.Gender;
import com.example.safariwebstore008.enums.Roles;
import com.example.safariwebstore008.models.User;
import com.example.safariwebstore008.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.beans.factory.annotation.Value;


import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableSwagger2
@Component
public class SafariWebstore008Application {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) throws MessagingException {
        SpringApplication.run(SafariWebstore008Application.class, args);

    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/products/*").allowedOrigins("http://localhost:8080");
            }
        };
    }

//    @PostConstruct
//    void createAdmin() {
//        User user = new User();
//        user.setIsEnabled(true);
//        user.setRoles(Roles.ADMIN);
//        user.setPassword(passwordEncoder.encode("12345"));
//        user.setEmail("admin@admin.com");
//        user.setGender(Gender.MALE);
////        user.setDateOfBirth(Date.valueOf("31/10/1991"));
//        userRepository.save(user);
//    }
}
