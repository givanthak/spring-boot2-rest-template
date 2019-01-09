package com.staxrt.sample.configuration;

import io.swagger.models.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String SIGN_UP_URL = "/api/v1/users";
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests() // Add a new custom security filter
                .antMatchers(String.valueOf(HttpMethod.POST), SIGN_UP_URL)
                .permitAll() // Only Allow Permission for create user endpoint
                .anyRequest()
                .authenticated()
                .and()
//                .addFilter(this.getJWTAuthenticationFilter()) // Add JWT Authentication Filter
//                .addFilter(
//                        new JWTAuthorizationFilter(authenticationManager())) // Add JWT Authorization Filter
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS); // this disables session creation on Spring Security
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**",
                new CorsConfiguration()
                        .applyPermitDefaultValues()); // Allow/restrict our CORS permitting requests from any
        // source
        return source;
    }
}
