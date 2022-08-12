package server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import server.filter.AuthFilter;
import server.service.impl.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)	//
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Autowired
	private UserDetailServiceImpl userDetailsServiceImpl;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {	
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		
		http.csrf().disable().authorizeRequests()
			.antMatchers("/api/auth/register", "/api/auth/login", "/api/images").permitAll()
			.antMatchers("/api/user/info", "/api/order/**", "/api/admin/**").authenticated();
		
		http.addFilter(new AuthFilter(authenticationManager(), userDetailsServiceImpl));
		
		// KHÔNG SỬ DỤNG SESSION
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	

}
