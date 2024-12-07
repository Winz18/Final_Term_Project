package hcmute.uni.final_term_project.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll() // Cho phép các request không cần đăng nhập
                        .anyRequest().authenticated() // Yêu cầu xác thực với tất cả các request còn lại
                )
                .formLogin(login -> login
                        .loginPage("/login") // Trang login tùy chỉnh
                        .defaultSuccessUrl("/home", true) // Chuyển hướng khi login thành công
                        .failureUrl("/login?error=true") // Chuyển hướng khi login thất bại
                        .permitAll() // Cho phép mọi người truy cập trang login
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL để logout
                        .logoutSuccessUrl("/login?logout=true") // Chuyển hướng khi logout thành công
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/login", "/logout")); // Không áp dụng CSRF cho các endpoint này
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Sử dụng BCrypt để mã hóa mật khẩu
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456")) // Mật khẩu mã hóa bằng BCrypt
                .roles("USER") // Cấp quyền USER
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
