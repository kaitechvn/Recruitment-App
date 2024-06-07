package com.example.recruitment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomJdbcDaoImpl implements UserDetailsService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String query = "SELECT username, password FROM user WHERE username = ?";
    return jdbcTemplate.queryForObject(query, new Object[]{username}, (rs, rowNum) ->
      User.withUsername(rs.getString("username"))
        .password(rs.getString("password"))
        .build()
    );
  }
}
