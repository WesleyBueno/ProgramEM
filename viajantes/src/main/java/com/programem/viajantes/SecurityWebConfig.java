package com.programem.viajantes;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter{

  @Autowired
  private ImplementsUserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
      http.csrf().disable().authorizeRequests()
      .antMatchers(HttpMethod.GET, "/").permitAll()
      .anyRequest().authenticated()
      .and().formLogin().permitAll()
      .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
protected void configure(AuthenticationManagerBuilder auth) throws Exception{
  auth.userDetailsService(userDetailsService)
  .passwordEncoder(new BCryptPasswordEncoder());
}

@Override
public void configure(WebSecurity web) throws Exception{
  web.ignoring().antMatchers("/materialize/**", "/style/**");
}
}