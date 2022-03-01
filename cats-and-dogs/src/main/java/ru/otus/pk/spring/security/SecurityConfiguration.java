package ru.otus.pk.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.http.HttpMethod.DELETE;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                .and().authorizeRequests().antMatchers("/", "/animals.html", "/owners.html", "/adoptions.html",
                        "/js/**").authenticated()

                .and().authorizeRequests().antMatchers("/actuator/**").authenticated()
                .and().authorizeRequests().antMatchers("/swagger-ui/**", "/v3/api-docs/**").authenticated()

//                .and().authorizeRequests().antMatchers(GET, "/api/v1/animals/**").permitAll()
                .and().authorizeRequests().antMatchers(DELETE, "/api/v1/**").hasAnyRole("ADMIN")
                .and().authorizeRequests().antMatchers("/api/v1/**").authenticated()
                .and().authorizeRequests().antMatchers("/**").denyAll()

                .and().formLogin() //.defaultSuccessUrl("/animals.html")
                .and().logout().logoutSuccessUrl("/login").permitAll();
//                .and().exceptionHandling().accessDeniedPage("/error403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
