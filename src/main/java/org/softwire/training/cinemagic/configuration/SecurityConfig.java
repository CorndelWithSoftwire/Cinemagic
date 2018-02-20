package org.softwire.training.cinemagic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * Configure authentication to use our DB.
     *
     * Override the authorities query to return ROLE_USER for every user.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, 'ROLE_ADMIN' from admins where username = ?");
    }

    /**
     * Configure Web Security
     * - Disable CSRF protection (don't worry about this for now!)
     * - Allow any user to access the root
     * - Any other pages require authentication
     * - Use the default Login and Logout forms
     * TODO: configure better
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .regexMatchers("^/api/admin.*").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
}
