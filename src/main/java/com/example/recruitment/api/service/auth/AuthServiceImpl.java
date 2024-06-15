package com.example.recruitment.api.service.auth;

import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.AuthLoginDtoIn;
import com.example.recruitment.api.dto.out.AuthLoginDtoOut;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;

    private final JwtEncoder jwtEncoder;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserDetailsService userDetailsService, JwtEncoder jwtEncoder,
                           PasswordEncoder passwordEncoder) {
      this.userDetailsService = userDetailsService;
      this.jwtEncoder = jwtEncoder;
      this.passwordEncoder = passwordEncoder;
    }

  @Override
  public AuthLoginDtoOut login(AuthLoginDtoIn loginDtoIn) {
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDtoIn.getUsername());

    if(!userDetails.isAccountNonExpired()){
      throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "user inactive");
    }

    if (!passwordEncoder.matches(loginDtoIn.getPassword(), userDetails.getPassword())) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "wrong password");
    }

    String role = userDetails.getAuthorities().iterator().next().getAuthority();

    return AuthLoginDtoOut.builder()
      .message("Login successfully")
      .accessToken(grantAccessToken(userDetails.getUsername(), role))
      .build();
  }

    private String grantAccessToken(String username, String role) {
      long iat = System.currentTimeMillis() / 1000;
      long exp = iat + Duration.ofHours(2).toSeconds();

      JwtEncoderParameters parameters = JwtEncoderParameters.from(
        JwsHeader
          .with(SignatureAlgorithm.RS256)
          .build(),
        JwtClaimsSet.builder()
          .subject("recruitment")
          .issuedAt(Instant.ofEpochSecond(iat))
          .expiresAt(Instant.ofEpochSecond(exp))
          .claim("username", username)
          .claim("role",  role)
          .build());

      try {
        return jwtEncoder.encode(parameters).getTokenValue();
      } catch (JwtEncodingException e) {
        log.error("Error: ", e);
        throw new RuntimeException(e);
      }
    }
  }

