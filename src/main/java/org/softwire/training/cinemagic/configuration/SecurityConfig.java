package org.softwire.training.cinemagic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * Security configuration for secure (i.e. admin) endpoints.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public SecurityConfig(@Value("${cinemagic.admin.username}") String adminUsername,
                          @Value("${cinemagic.admin.password}") String adminPassword,
                          AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(adminUsername).password(adminPassword).roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for simplicity
                .csrf().disable()
                // By default allow all requests (use @Secured to mark others)
                .authorizeRequests().anyRequest().permitAll()
                // Configure exception handling
                .and().exceptionHandling()
                    .authenticationEntryPoint((req, res, auth) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                // Configure login
                .and().formLogin().loginProcessingUrl("/api/admin/login")
                    .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT))
                    .failureHandler((req, res, auth) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                // Configure logout
                .and().logout().logoutUrl("/api/admin/logout")
                    .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT));
    }
}
