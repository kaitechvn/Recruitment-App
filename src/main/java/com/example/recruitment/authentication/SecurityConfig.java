package com.example.recruitment.authentication;

import com.example.recruitment.common.exception.ApiException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2

public class SecurityConfig {
  @Autowired
  private DataSource dataSource;

  @Autowired
  @Qualifier("customEntryPoint")
  AuthenticationEntryPoint authEntryPoint;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors(Customizer.withDefaults())
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(request -> request
        .requestMatchers("/swagger-ui/**", "/auth/login", "/actuator/**").permitAll()
        .anyRequest().authenticated())
      .oauth2ResourceServer(cfg -> cfg
        .authenticationEntryPoint(authEntryPoint)
        .jwt(jwt -> {
          try {
            jwt
//              .jwtAuthenticationConverter(jwtAuthenticationConverter())
              .decoder(NimbusJwtDecoder
                .withPublicKey(readPublicKey(new ClassPathResource("public.pem"))).build());
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        })
      )
      .build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    try {
      return new NimbusJwtEncoder(new ImmutableJWKSet<>(
        new JWKSet(new RSAKey.Builder(readPublicKey(new ClassPathResource("public.pem")))
          .privateKey(readPrivateKey(new ClassPathResource("private.pem"))).build())));
    } catch (Exception e) {
      log.error("Error: ", e);
      throw new RuntimeException(e);
    }
  }

  private static RSAPublicKey readPublicKey(Resource resource) throws Exception {
    return RsaKeyConverters.x509().convert(resource.getInputStream());
  }

  private static RSAPrivateKey readPrivateKey(Resource resource) throws Exception {
    return RsaKeyConverters.pkcs8().convert(resource.getInputStream());
    }

  @Bean
  public UserDetailsService userDetailsService() {
    JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
    jdbcDao.setDataSource(dataSource); // Set the data source
    return jdbcDao;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//      JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//      // Custom converter if needed
//      return converter;
//    }
}


