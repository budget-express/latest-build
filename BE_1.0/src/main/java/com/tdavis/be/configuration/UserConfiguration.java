package com.tdavis.be.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class UserConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth
			.jdbcAuthentication()
				.dataSource(dataSource)
				.authoritiesByUsernameQuery(
					"select app_user.name, role.name from app_user" +
					" join app_user_role on app_user.id = app_user_role.users_id" +
					" join role on app_user_role.roles_id = role.id" +
					" where app_user.name = ?")
				.usersByUsernameQuery("select name,password,enabled from app_user where name = ?")
				.passwordEncoder(new BCryptPasswordEncoder());
		// @formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/resources/**", "/vendors/**", "/build/**", "/js/**", "/css/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().hasRole("USER")
				.and()
			.formLogin()
				.loginPage("/login.html")
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.loginProcessingUrl("/j_spring_security_check")
				.defaultSuccessUrl("/")
				.permitAll()
				.and()
			.logout()
				/* @see: org.springframework.security.config.annotation.web.configurers.LogoutConfigurer#logoutUrl */
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.and()
			.rememberMe();
		// @formatter:on
	}

}