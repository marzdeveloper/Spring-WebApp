package advPro.GestioneDispositivi.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import advPro.GestioneDispositivi.Services.UserDetailsServiceDefault;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().
			antMatchers("/login").permitAll().
			antMatchers("/user").hasAnyRole("ADMIN").
			antMatchers("/sender").hasAnyRole("USER", "ADMIN").
			antMatchers("/team/**").hasAnyRole("USER", "ADMIN").
			antMatchers("/dispositivi/**").hasAnyRole("USER", "ADMIN").
			antMatchers("/employee/**").hasAnyRole("USER", "ADMIN").
			antMatchers("/positioning").hasAnyRole("USER", "ADMIN").
			antMatchers("/positioning/**").hasAnyRole("USER", "ADMIN").
			antMatchers("/location/**").hasAnyRole("USER", "ADMIN").
			antMatchers("/css/**").permitAll().
			antMatchers("/images/**").permitAll().
			antMatchers("/").permitAll().
			antMatchers("/**").hasAnyRole("USER", "ADMIN").
				and().formLogin().loginPage("/login").defaultSuccessUrl("/dispositivi", true)
					.successForwardUrl("/dispositivi")
					.defaultSuccessUrl("/dispositivi")
					.failureUrl("/login?error=true").permitAll().
				and().logout().logoutSuccessUrl("/") // NB se commentiamo
														// questa riga,
														// viene richiamata
														// /login?logout
					.invalidateHttpSession(true).permitAll().
			and().csrf().disable();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceDefault();
	};

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService()).passwordEncoder(this.passwordEncoder);

	}

}