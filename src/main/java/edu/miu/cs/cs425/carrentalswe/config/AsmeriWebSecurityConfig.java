package edu.miu.cs.cs425.carrentalswe.config;

import edu.miu.cs.cs425.carrentalswe.service.ERentalUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class AsmeriWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public AsmeriWebSecurityConfig(ERentalUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/static/**").permitAll()
                .antMatchers("/eRental/images/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/","/eRental","/eRental/home","/eRental/public/**").permitAll()
                .antMatchers("/eRental/secured/users/**").hasRole("ADMIN")
                .antMatchers("/eRental/secured/vehicles/**").hasRole("FRONT")
                .antMatchers("/eRental/secured/services/contractor/**").hasRole("CONTRACTOR")
                .antMatchers("/eRental/secured/services/client/**").hasRole("CLIENT")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/eRental/public/login")
                .successForwardUrl("/eRental/secured/home")
                .defaultSuccessUrl("/eRental/secured/home",true)
                .failureUrl("/eRental/public/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/eRental/public/logout"))
                .logoutSuccessUrl("/eRental/public/login?logout")
                .permitAll()
                .and()
                .exceptionHandling();

    }
}
