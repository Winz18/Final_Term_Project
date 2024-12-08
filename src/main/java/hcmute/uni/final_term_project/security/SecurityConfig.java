package hcmute.uni.final_term_project.security;

import hcmute.uni.final_term_project.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Disable frame options
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/", "/register", "/about").permitAll() // Cho phép các request không cần đăng nhập
                        .anyRequest().authenticated() // Yêu cầu xác thực với tất cả các request còn lại
                )
                .formLogin(login -> login
                        .loginPage("/login") // Trang login tùy chỉnh
                        .defaultSuccessUrl("/user/home", true) // Chuyển hướng khi login thành công
                        .failureUrl("/login?error=true") // Chuyển hướng khi login thất bại
                        .permitAll() // Cho phép mọi người truy cập trang login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL để logout
                        .logoutSuccessUrl("/login?logout=true") // Chuyển hướng khi logout thành công
                        .permitAll()
                )
                // Tắt CSRF để dễ dàng test
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
