package hcmute.uni.final_term_project;

import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FinalTermProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalTermProjectApplication.class, args);
    }

}
