package com.poly.datn.Config;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            com.poly.datn.Entity.User.User user = userService.findByUsernameUser(username);
            if (user == null || user.getIsDeleted() == IsDelete.DELETED.ordinal()) {
                throw new UsernameNotFoundException(username + " not found!");
            }
            String password = "{noop}" + user.getPassword(); // Thêm {noop} trước mật khẩu
            String[] roles = user.getAccounts().stream()
                    .map(er -> er.getRole().getId())
                    .toArray(String[]::new);
            return User.withUsername(username)
                    .password(password)
                    .roles(roles)
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder delegatingPasswordEncoder =
                (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return delegatingPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/order/**").authenticated()
                                .requestMatchers("/admin/**").hasAnyRole("STAF", "ADM")
                                .requestMatchers("/rest/authorities").hasRole("ADM")
                                .requestMatchers("/**").permitAll()

                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/security/login/form")
                                .loginProcessingUrl("/security/login")
                                .defaultSuccessUrl("/security/login/success", false)
                                .failureUrl("/security/login/error")
                )
                .rememberMe(rememberMe ->
                        rememberMe
                                .tokenValiditySeconds(86400)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/security/unauthorized")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/security/logoff")
                                .logoutSuccessUrl("/security/logoff/success")
                );

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
    }
}
