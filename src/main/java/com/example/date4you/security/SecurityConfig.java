package com.example.date4you.security;

import com.example.date4you.repository.UnicornRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {

private final UnicornRepository unicornRepository;

    public SecurityConfig(UnicornRepository unicornRepository) {
        this.unicornRepository = unicornRepository;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests((auth) -> auth
//                .antMatchers("/", "/index")
//                .permitAll()
//                .anyRequest()
//                .authenticated())
//                .httpBasic(withDefaults());
////                .formLogin(withDefaults());
//        return http.build();
//    }
//
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("pass"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//
//    }

    @Configuration
    public class SecurityConfiguration {
        @Bean
        public UserDetailsService userDetailsService() {
            return new UnicornDetailsService();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService( userDetailsService() );
            return authProvider;
        }
        @Bean
        public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
            http.authorizeRequests()
                    .antMatchers( "/", "/register", "/create").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin(withDefaults())
                    .logout()
                    .logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
                    .logoutSuccessUrl( "/login" )
                    .deleteCookies( "JSESSIONID" )
                    .invalidateHttpSession( true ).permitAll()
            ;

            return http.build();
        }
        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            return ( web ) -> web.ignoring().antMatchers( "/images/**", "/js/**", "/webjars/**" );
        }
    }

}
