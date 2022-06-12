package my.learning.taco_cloud.security;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import my.learning.taco_cloud.UserRepositoryUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final UserRepositoryUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//            .dataSource(dataSource)
//            .usersByUsernameQuery(
//                "select username, password, enabled from Users where username=?")
//            .authoritiesByUsernameQuery(
//                "select username, authority from UserAuthorities where username=?")
//            .passwordEncoder(new BCryptPasswordEncoder());
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uuid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .passwordAttribute("passcode")
//                .and()
//                .contextSource()
//                    .root("dc=tacocloud,dc=com")
//                    .ldif("classpath:users.ldif");
////                    .url("ldap://tacocloud.com:389/dc=tacocloud,dc=com");
//    }
}
