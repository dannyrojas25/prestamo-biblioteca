package com.ceiba.biblioteca.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Le indica al contenedor de Spring que es una clase de seguridad al momento de arrancar la app.
@EnableWebSecurity //Indicamos que se activa la seguridad Web en la app, además, será una clase que contendrá toda la configuración referente a la seguridad.
public class SecurityConfig {
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private static final String ADMIN = "ADMIN";
    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    //Este Bean va a verificar la información de los usuarios que se logueen en el app.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Con este Bean nos encargaremos de encriptar las contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Este Bean incorpora el filtro de seguridad de JWT que creamos en la clase JwtAuthenticationFilter
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    //Este Bean establece una cadena de filtros de seguridad en nuestra app. Y es aquí donde se determina los permisos según los roles para acceder a la app.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                    .exceptionHandling() //Permitimos el manejo de excepciones
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) //Nos establece un punto de entrada personalizado de autenticación para el manejo de autenticaciones no autorizadas
                .and()
                .sessionManagement() //Permite la gestión de sessiones
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests() //Toda petición http debe ser autorizada
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/doc/**").permitAll()
                    .requestMatchers("/v3/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/libro/insertar").hasAuthority(ADMIN)
                    .requestMatchers(HttpMethod.GET,"/api/usuario/obtener/**").hasAnyAuthority(ADMIN , "USER")
                    .requestMatchers(HttpMethod.GET,"/api/celular/listarId/**").hasAnyAuthority(ADMIN , "USER")
                    .requestMatchers(HttpMethod.DELETE,"/api/celular/eliminar/**").hasAuthority(ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/celular/actualizar").hasAuthority(ADMIN)
                    .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
