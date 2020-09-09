package com.rodmontiel.ec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rodmontiel.ec.security.JwtAuthenticationEntryPoint;
import com.rodmontiel.ec.security.JwtRequestFilter;
import com.rodmontiel.ec.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
	        .authorizeRequests()
	        .antMatchers(
	        		"/csrf**", "/csrf/**",
	        		"/swagger-ui.html",
	        		"/swagger-resources**", "/swagger-resources/**",
	        		"/v2/api-docs**", "/v2/api-docs/**",
	        		"/webjars/springfox-swagger-ui**",
	        		"/webjars/springfox-swagger-ui/**",
	        		"/signin", "/signin/",
	        		"/signup", "/signup/"
        		).permitAll()
	        .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
	        .antMatchers(
	        		"/categories/admin**", "/categories/admin/**",
	        		"/expenses/admin**", "/expenses/admin/**",
	        		"/incomes/admin**","/incomes/admin/**"
	        	).hasAuthority("ADMIN")
	        .anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
      
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
    	BCryptPasswordEncoder pEnc = new BCryptPasswordEncoder();
        return pEnc; 
    }
    
    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPointBean() throws Exception{
        return new JwtAuthenticationEntryPoint();
    }

    @Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

    
}
