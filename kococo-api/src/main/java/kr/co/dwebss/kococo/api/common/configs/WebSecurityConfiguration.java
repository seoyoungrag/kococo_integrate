package kr.co.dwebss.kococo.api.common.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.co.dwebss.kococo.api.common.components.security.AuthFailureHandler;
import kr.co.dwebss.kococo.api.common.components.security.AuthsuccessHandler;
import kr.co.dwebss.kococo.api.common.components.security.HttpAuthenticationEntryPoint;
import kr.co.dwebss.kococo.api.common.components.security.HttpLogoutSuccessHandler;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private HttpAuthenticationEntryPoint authencationEntryPoint;

	@Autowired
	private AuthsuccessHandler authsuccessHandler;

	@Autowired
	private AuthFailureHandler authFailureHandler;

	@Autowired
	private HttpLogoutSuccessHandler httpLogoutSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(authencationEntryPoint);
		
		http.csrf().disable();
		http.formLogin().permitAll().loginProcessingUrl("/login").usernameParameter("USERNAME").passwordParameter("PASSWORD")
		.successHandler(authsuccessHandler)
		.failureHandler(authFailureHandler)
		.and()
		.logout()
		.permitAll()
		.logoutRequestMatcher(new AntPathRequestMatcher("logout"))
		.logoutSuccessHandler(httpLogoutSuccessHandler)
		.and().sessionManagement()
		.maximumSessions(1);
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/api/**").permitAll()
		.antMatchers(HttpMethod.POST,"/api/**").permitAll()
		.antMatchers(HttpMethod.PUT,"/api/**").permitAll()
		.antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
		//.antMatchers(HttpMethod.DELETE,"/api/**").permitAll()
		.anyRequest().authenticated();
	}
}
