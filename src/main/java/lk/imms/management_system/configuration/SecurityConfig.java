package lk.imms.management_system.configuration;


import lk.imms.management_system.asset.userManagement.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll();

        //For developing easy to give permission all link

   /*     http.

                authorizeRequests()
                //Anytime users can access without login
                .antMatchers(
                        "/index",
                        "/favicon.ico",
                        "/img/**",
                        "/css/**",
                        "/js/**",
                        "/webjars/**").permitAll()
                .antMatchers("/login", "/select/**").permitAll()

                //Need to login for access those are
                .antMatchers("/employee/**").hasRole("ADMIN")
                .antMatchers("/employee1/**").hasRole("MANAGER")
                .antMatchers("/user/**").hasRole("MANAGER")
                .antMatchers("/invoiceProcess/add").hasRole("CASHIER")
                .anyRequest()
                .authenticated()
                .and()
                // Login form
                .formLogin()
                .loginPage("/login")
                //Username and password for validation
                .usernameParameter("username")
                .passwordParameter("password")
                *//*todo -> need to change *//*
                .defaultSuccessUrl("/employee")
                .and()
                //Logout controlling
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .and()
                .exceptionHandling()
                //Cross site disable
                .and()
                .csrf()
                .disable();*/
        /* //Header used to Enable HTTP Strict Transport Security (HSTS)
                .headers()
                .httpStrictTransportSecurity()
                .includeSubdomains(true)
                .maxAgeSeconds(31536000);
                */

    }

    ;

}

