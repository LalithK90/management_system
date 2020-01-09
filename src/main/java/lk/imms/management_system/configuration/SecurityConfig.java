package lk.imms.management_system.configuration;


import lk.imms.management_system.asset.userManagement.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] ALL_PERMIT_URL = {"/favicon.ico", "/img/**", "/css/**", "/js/**", "/webjars/**",
            "/actuator/**", "/login", "/select/**"};

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
 /*       http.csrf().disable();
        http.authorizeRequests().antMatchers("/").permitAll();
*/
        // For developing easy to give permission all link
        http
                .authorizeRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        //Anytime users can access without login
                                        //to see actuator details
                                        .antMatchers(ALL_PERMIT_URL).permitAll()
                                        //this is used the normal admin to give access every url mapping
                                        .antMatchers("/").hasRole("/ADMIN")
                                        //Need to login for access those are
                                        /*.antMatchers("/employee/**").hasRole("ADMIN")
                                        .antMatchers("/employee1/**").hasRole("MANAGER")
                                        .antMatchers("/user/**").hasRole("ADMIN")
                                        .antMatchers("/petition/**").hasRole("ADMIN")
                                        .antMatchers("/minutePetition/**").hasRole("MANAGER")
                                        .antMatchers("/invoiceProcess/add").hasRole("CASHIER")
                                      */.anyRequest()
                                        .authenticated())
                // Login form
                .formLogin(
                        formLogin ->
                                formLogin
                                        .loginPage("/login")
                                        //Username and password for validation
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .defaultSuccessUrl("/mainWindow"))
                //session management
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement
                                        .maximumSessions(1)
                                        .maxSessionsPreventsLogin(true)
                                        .expiredUrl("/login"))
                //Logout controlling
                .logout(
                        logout ->
                                logout
                                        .invalidateHttpSession(true)
                                        .clearAuthentication(true)
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/login"))
                //Port mapper
          /*      .portMapper(portMapper ->
                                    portMapper
                                            .http(9090).mapsTo(8080))*/
                //Cross site disable
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling();

        /* //Header used to Enable HTTP Strict Transport Security (HSTS)
                .headers()
                .httpStrictTransportSecurity()
                .includeSubdomains(true)
                .maxAgeSeconds(31536000);
                */
    }

}

