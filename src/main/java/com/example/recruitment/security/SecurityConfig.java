package com.example.recruitment.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {

  @Autowired
  @Qualifier("customUnauthorizedEntryPoint")
  private AuthenticationEntryPoint authEntryPoint;

  @Autowired
  @Qualifier("customForbiddenEntryPoint")
  private AccessDeniedHandler forbiddenEntryPoint;

  @Value("${jwt.public-key-path}")
  private Resource publicKeyResource;

  @Value("${jwt.private-key-path}")
  private Resource privateKeyResource;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
                  .cors(Customizer.withDefaults())
                  .csrf(csrf -> csrf.disable())
                  .authorizeHttpRequests(request -> request
                            .requestMatchers(
                              "/swagger-ui/**",
                              "/v3/api-docs/**",
                              "/auth/login/**",
                              "/actuator/**",
                              "/sentry/**"
                            )
                            .permitAll()
                            .anyRequest().authenticated())


                  .oauth2ResourceServer(cfg -> cfg
                            .authenticationEntryPoint(authEntryPoint)
                            .accessDeniedHandler(forbiddenEntryPoint)
                            .jwt(jwt -> jwt
                              .decoder(jwtDecoder())
                              .jwtAuthenticationConverter(jwtAuthConverter())
                            )
                  )
      .build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    try {
      RSAPublicKey publicKey = readPublicKey(publicKeyResource);
      RSAPrivateKey privateKey = readPrivateKey(privateKeyResource);
      RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
      JWKSet jwkSet = new JWKSet(rsaKey);
      return new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet));
    } catch (Exception e) {
      throw new RuntimeException("Failed to create JwtEncoder", e);
    }
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    try {
      RSAPublicKey publicKey = readPublicKey(publicKeyResource);
      return NimbusJwtDecoder.withPublicKey(publicKey).build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to create JwtDecoder", e);
    }
  }

  private static RSAPublicKey readPublicKey(Resource resource) throws Exception {
    return RsaKeyConverters.x509().convert(resource.getInputStream());
  }

  private static RSAPrivateKey readPrivateKey(Resource resource) throws Exception {
    return RsaKeyConverters.pkcs8().convert(resource.getInputStream());
    }

  @Bean
  public JwtAuthenticationConverter jwtAuthConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName("role");
    grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return converter;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new CustomDaoImpl();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}


