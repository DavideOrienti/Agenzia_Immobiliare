package it.uniroma3.siw.authentication;


import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The AuthConfiguration is a Spring Security Configuration.
 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
 */
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * The datasource is automatically injected into the AuthConfiguration (using its getters and setters)
     * and it is used to access the DB to get the Credentials to perform authentiation and authorization
     */
    @Autowired
    DataSource datasource;

    /**
     * This method provides the whole authentication and authorization configuration to use.
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                // authorization paragraph: qui definiamo chi può accedere a cosa
//                .authorizeRequests()
//              
//                // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
//                .antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**","/immobili/**","/immobile/**","/agente/**","/agenti/**","/").permitAll()
//                // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
//                .antMatchers(HttpMethod.POST, "/login", "/register","/ticketForm").permitAll()
//                
//
//                .antMatchers(HttpMethod.GET, "/admin/","/agenteForm","/immobileForm").hasAnyAuthority(ADMIN_ROLE)
//                .antMatchers(HttpMethod.POST, "/admin/**","/agente/**","/agenti/**","/immobile/**","/immobili/**").hasAnyAuthority(ADMIN_ROLE)
//               // .antMatchers(HttpMethod.GET "/piatti/**", "/piatto/**").hasAnyAuthority(DEFAULT_ROLE)
//                
//                // tutti gli utenti autenticati possono accere alle pagine rimanenti 
//                .anyRequest().authenticated()
//
//                // login paragraph: qui definiamo come è gestita l'autenticazione
//                // usiamo il protocollo formlogin 
//                .and().formLogin()
//                // la pagina di login si trova a /login
//                // NOTA: Spring gestisce il post di login automaticamente
//                .loginPage("/login")
//                // se il login ha successo, si viene rediretti al path /default
//                .defaultSuccessUrl("/default")
//
//                // logout paragraph: qui definiamo il logout
//                .and().logout()
//                // il logout è attivato con una richiesta GET a "/logout"
//                .logoutUrl("/logout")
//                // in caso di successo, si viene reindirizzati alla /index page
//                .logoutSuccessUrl("/index")        
//                .invalidateHttpSession(true)
//                .clearAuthentication(true).permitAll();
//    }
    //qui facciamo il login iniziale
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf()
        .disable()
        //chi puo accedere a cosa
        .authorizeRequests()
        // chiunque puo accedere alle pagine index, login, register, ai css e alle immagini
        .antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**").permitAll()
        // chiunque puo effettuare il login e registrarsi
        .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
        // utenti ADMIN possono accedere a risorse con path /admin/
        .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
        .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
        // utenti autenticati possono accedere alle pagine rimanenti 
        .anyRequest().authenticated()

        // gestione autenticazione 
        .and().formLogin()
        // la pagina di login 
        .loginPage("/login")
        // se il login ha successo, rediretti /default
        .defaultSuccessUrl("/default")

        // logout paragraph: qui definiamo il logout
        .and().logout()
        // il logout attivato 

        .logoutUrl("/logout")

        // in caso di successo, si viene reindirizzati alla /index page
        .logoutSuccessUrl("/index")

        .invalidateHttpSession(true)
        .clearAuthentication(true).permitAll();
    }

    /**
     * This method provides the SQL queries to get username and password.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                //use the autowired datasource to access the saved credentials
                .dataSource(this.datasource)
                //retrieve username and role
                .authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    /**
     * This method defines a "passwordEncoder" Bean.
     * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
//
//
//
//@Configuration
//@EnableWebSecurity
//
//public class AuthConfiguration extends WebSecurityConfigurerAdapter {
//	@Autowired 
//	DataSource datasource;
//
//
//
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//	http.authorizeRequests() // authorization paragraph: qui definiamo chi può accedere a cosa
//	.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**").permitAll() //chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
//	.antMatchers(HttpMethod.POST, "/login", "/register").permitAll() // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
//	.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE) // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
//	.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
//	.anyRequest().authenticated() // tutti gli utenti autenticati possono accere alle pagine rimanenti
//	// login paragraph: qui definiamo come è gestita l'autenticazione
//	.and().formLogin() // usiamo il protocollo formlogin 
//	.loginPage("/login") // la pagina di login si trova a /login
//	.defaultSuccessUrl("/default") // se il login ha successo, si viene rediretti al path /default
//	// logout paragraph: qui definiamo il logout
//	.and()
//	.logout()
//	.logoutUrl("/logout") // il logout è attivato con una richiesta GET a "/logout"
//	.logoutSuccessUrl("/index") // in caso di successo, si viene reindirizzati alla /index page
//	.invalidateHttpSession(true)
//	.deleteCookies("JSESSIONID")
//	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//	.clearAuthentication(true).permitAll();
//}
//
///**
//     * This method provides the SQL queries to get username and password.
//     */
////	    @Override
////	    public void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.jdbcAuthentication()
////		.dataSource(this.datasource) //use the autowired datasource to access the saved credentials
////		//retrieve username and role
////		.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
////		//retrieve username, password and a bool specifying whether the user is enabled (always enabled for us) 
////		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
////		    }
//
//
//@Override
//public void configure(AuthenticationManagerBuilder auth) throws Exception {
//	auth.jdbcAuthentication()
//	//use the autowired datasource to access the saved credentials
//	.dataSource(this.datasource)
//	//retrieve username and role
//	.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
//	//retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
//	.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
//}
///**
//     * This method defines a "passwordEncoder" Component (a Bean in the EE slang).
//     * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
//     */
//
//
//   @Bean
//     PasswordEncoder passwordEncoder() {
//	  return new BCryptPasswordEncoder();
//     }
//
//    }
