package spring.security.encoder.case1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author Anthony.liu
 * @Description
 * @Date 2020/12/30
 */

@Configuration
@EnableWebSecurity
public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthProvider authProvider;

//    @Override
//    public void configure(HttpSecurity http) throws Exception{
//        super.configure(http);
//        http
//                .authorizeRequests()
//                .antMatchers("/register123/**","/login123/**","/login").permitAll().antMatchers("/**").authenticated()
//        .and().csrf().disable();
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder.encode("123"))
                .roles("Admin")
                .and()
                .passwordEncoder(passwordEncoder)
                .and().authenticationProvider(authProvider);
    }
}
