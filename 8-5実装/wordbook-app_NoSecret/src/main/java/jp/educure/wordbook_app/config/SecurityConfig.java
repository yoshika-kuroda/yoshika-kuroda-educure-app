package jp.educure.wordbook_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
    
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/","/css/**","/js/**","/oauth2/**","/logout/google").permitAll()
            .requestMatchers("/admin/**").authenticated()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth -> oauth
            .loginPage("/")
            .defaultSuccessUrl("/login/success",true)
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/logout/google")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID")
        );
        return http.build();
    }
    
}
