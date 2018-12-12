package cgg.informatique.jfl.webSocket.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecuriteWebConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MonUserDetailsService monUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                //Modification pour REST
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()

                .authorizeRequests()

                //Permettre l'accès à la console H2 uniquement au rôle ADMIN
                .antMatchers("/kumite/**", "/kumite/").authenticated()
                .antMatchers("/examen/**", "/examen/").hasAnyAuthority("SENSEI","VENERABLE")
                .antMatchers("/AvatarsREST/**", "/AvatarsREST/").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(new MySavedRequestAwareAuthenticationSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/dojo")
                .and()
                .csrf()
                .ignoringAntMatchers("/**", "/" )//ne pas appliquer le CSRF pour /consoleBD
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();*/

        http.authorizeRequests()
                .antMatchers("/kumite/**", "/kumite/").authenticated()
                .antMatchers("/examen/**", "/examen/").hasAnyAuthority("SENSEI", "VENERABLE")
                .anyRequest().permitAll()
                .and()
//Activer le formulaire pour login
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
//pour la console H2
                .and()
                .csrf()
                .ignoringAntMatchers("/consoleBD/**").and()
                .headers()
                .frameOptions()
                .sameOrigin();
    }

    //Initialiser la méthode pour s’identifier
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //Définir les méthodes qui permettront l’identification monUserDetailsService et
    //le cryptage des mots de passe.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(monUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    //Définir la méthode de cryptage des mots de passe
    //https://en.wikipedia.org/wiki/Bcrypt
    //La valeur par defaut est 10
    //https://docs.spring.io/spring-security/site/docs/4.2.7.RELEASE/apidocs/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html
    //https://www.browserling.com/tools/bcrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    };

}