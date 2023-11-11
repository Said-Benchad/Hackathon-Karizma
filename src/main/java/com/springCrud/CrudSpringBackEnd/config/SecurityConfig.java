package com.springCrud.CrudSpringBackEnd.config;

import com.springCrud.CrudSpringBackEnd.Service.UserDetailServiceImp;
import com.springCrud.CrudSpringBackEnd.security.JwtAuthenticationEntryPoint;
import com.springCrud.CrudSpringBackEnd.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImp UserDetailServiceImp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationEntryPoint point;

    @Autowired
    private JwtAuthenticationFilter filter;

    /*@Autowired
    private LogoutHandler logoutHandler;*/

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(auth-> {
                            try {
                                auth.requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).permitAll()
                                        .requestMatchers(mvcMatcherBuilder.pattern("/auth/signup")).permitAll()
                                        .requestMatchers(mvcMatcherBuilder.pattern("/messages"))

                                        .authenticated()

                                        .anyRequest()
                                        .authenticated()
                                        .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                        .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);;
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                );

        /*
       http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                //.addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                        (request, response, authentication) ->
                                SecurityContextHolder.clearContext()
                )
        ;*/
        return http.build();


    }
 /*
    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            // Clear the session cookies
            request.getSession().invalidate();

            // Clear any other session data here
        };
    }
*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider Provider = new DaoAuthenticationProvider();
        Provider.setUserDetailsService(UserDetailServiceImp);
        Provider.setPasswordEncoder(passwordEncoder);

        return Provider;
    }

    @Bean
    public AuthenticationManager authenticationManagerSecurityConfig(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
