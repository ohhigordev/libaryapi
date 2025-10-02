package io.github.ohhigordev.libaryapi.config;

import io.github.ohhigordev.libaryapi.Security.CustomUserDetailsService;
import io.github.ohhigordev.libaryapi.Security.LoginSocialSuccessHandler;
import io.github.ohhigordev.libaryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   LoginSocialSuccessHandler successHandler) throws Exception{

        // Aqui basicamente fizemos a configuração padrão do nosso Spring
        return http
                .csrf(AbstractHttpConfigurer::disable) //Aplicação que ultilizamos quando queremos proteger paginas web (Desabilitamos essa classe)
                .formLogin(configurer ->{
                    configurer.loginPage("/login").permitAll(); // Aqui permitimos que qualquer pessoa possa acessar a nossa página de login
                })
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->{

                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios").permitAll();
                    // Aqui significa que apenas usuários com a role "ADMIN" podem fazer operações com a classe autor

                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(successHandler);
                })
                .build();
    }
    /*
    Aqui Vamos declarar um Bean que será um codificador para que as senhas que declaramos nos bancos
    em memória dos nossos usuários sejam criptografadas assim melhorando a segurança da nossa aplicação
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    // Criando um repositório de usuários em memória
//    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){
//        UserDetails user1 = User.builder()
//                .username("usuario")
//                .password(encoder.encode("123"))
//                .roles("USER") // Geralmente colocamos as 'roles' em caixa alta;
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("321"))
//                .roles("ADMIN") // Geralmente colocamos as 'roles' em caixa alta;
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);

        return new CustomUserDetailsService(usuarioService);
    }

}
