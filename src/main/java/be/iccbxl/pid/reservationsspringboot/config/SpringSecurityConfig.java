package be.iccbxl.pid.reservationsspringboot.config;

import java.util.Map;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;



@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
	//…
    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/api/public/**")  // Ajouter cette ligne pour ignorer CSRF pour les endpoints publics
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin").hasRole("admin");
                    auth.requestMatchers("/user").hasRole("member");
                    
                    //API
                    auth.requestMatchers("/api/public/**").permitAll(); // Endpoints publics
                    auth.requestMatchers("/api/admin/**").hasRole("admin"); // Endpoints réservés aux administrateurs
    
                    auth.anyRequest().permitAll();
                })
                // Reste de la configuration inchangé
                .build();
    }
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
@RestController
public static class CsrfController {
    @GetMapping("/csrf")
    public Map<String, String> csrf(CsrfToken token) {
        return Map.of(
            "token", token.getToken(),
            "headerName", token.getHeaderName(),
            "parameterName", token.getParameterName()
        );
    }}
}
