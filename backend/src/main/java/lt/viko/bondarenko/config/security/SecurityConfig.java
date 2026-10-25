package lt.viko.bondarenko.config.security;

import lt.viko.bondarenko.util.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Add CORS
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/v1/login/**", "/api/v1/sign-up/**",
                                        "/login/oauth2/code/google", "/api/v1/payments/webhook",
                                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                                .permitAll()
                                .requestMatchers("/api/v1/identity/**")
                                .authenticated()
                                .requestMatchers("/api/v1/user/email-request",
                                        "/api/v1/user/email-code/**")
                                .hasRole("NOT_CONFIRMED")
                                .requestMatchers("/api/v1/user/**", "/api/v1/projects/**",
                                        "/api/v1/tasks/**", "/api/v1/notifications/**",
                                        "/api/v1/payments/create-checkout-session", "/api/v1/report")
                                .hasRole("USER")
                                .requestMatchers("/api/v1/admin-dashboard/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/moderator-dashboard/**").hasRole("MODERATOR")
                                .anyRequest()
                                .denyAll())
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("http://localhost:3000"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(List.of("*"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
