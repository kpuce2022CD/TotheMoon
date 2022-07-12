package Youtube.SpringbootServer.config;


import Youtube.SpringbootServer.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomOAuth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/members/add").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/oauth2/authorization/google/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/getcomments/**").permitAll()
                .antMatchers("/findcomment/**").permitAll()
                .antMatchers("/interest/**").permitAll()
                .antMatchers("/timeline/**").permitAll()
                .antMatchers("/videoinfo/**").permitAll()
                .antMatchers("/getkeyword/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/*.ico").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/").permitAll()
                .and()
                .oauth2Login().loginPage("/login")
                .userInfoEndpoint()
                .userService(customOauth2UserService);
    }
}
