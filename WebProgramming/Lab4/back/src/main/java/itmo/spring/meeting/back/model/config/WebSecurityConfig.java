package itmo.spring.meeting.back.model.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/signUp").permitAll()
                    .anyRequest()
                    .authenticated()
                )
                .formLogin(formLogin -> formLogin
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/main")
                );
//

        return http.build();
    }
}