package com.example.expensetrackerapi.config;

import com.example.expensetrackerapi.security.CustomUserDetailService;
import com.example.expensetrackerapi.security.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Bean
    public JWTRequestFilter authenticationJwtTokenFilter() {
        return new JWTRequestFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        (1) basic
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/login", "/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();

//        (2) JWT
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        (1)
//        auth.inMemoryAuthentication()
//                .withUser("test1").password("password").authorities("admin")
//                .and()
//                .withUser("test2").password("password").authorities("user")
//                .and()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());

//        (2)
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//        UserDetails user1 = User.withUsername("test3").password("password").authorities("admin").build();
//        UserDetails user2 = User.withUsername("test4").password("password").authorities("user").build();
//
//        userDetailsManager.createUser(user1);
//        userDetailsManager.createUser(user2);
//
//        auth.userDetailsService((userDetailsManager));

//        (3)
        auth.userDetailsService(userDetailService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
