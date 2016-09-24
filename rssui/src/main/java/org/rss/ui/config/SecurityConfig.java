package org.rss.ui.config;

import org.rss.ui.security.ProperCookieClearingLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Alain on 18/09/2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	//@Autowired
	//private SessionRegistry sessionRegistry;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				//.antMatchers("/", "/home").permitAll()
				.antMatchers("/index.html").permitAll()
				.anyRequest().authenticated()
				//.anyRequest().permitAll()
				/*.and()
				.formLogin()
				.loginPage("/login")
				//.permitAll()*/
				.and()
				.formLogin()
					.defaultSuccessUrl("/index.html")
					//.loginPage("/login")
					//.successForwardUrl("/index.html")
				.and()
				.logout()
					//.logoutUrl("/logout")
					//.deleteCookies("remove")
					.deleteCookies("JSESSIONID")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.addLogoutHandler(new ProperCookieClearingLogoutHandler("JSESSIONID","USER"))
					.addLogoutHandler(new SecurityContextLogoutHandler())
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/index.html")
					//.permitAll()
				/*.and()
				.sessionManagement()
					.maximumSessions(1)
					//.maxSessionsPreventsLogin(true)
					//.expiredUrl("/accessDenied")
					.sessionRegistry(sessionRegistry)*/
				//.and()
				//.httpBasic()
				;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
}
