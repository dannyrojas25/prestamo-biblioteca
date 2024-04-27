package com.ceiba.biblioteca.controlador;

import com.ceiba.biblioteca.dto.AuthRespuestaDTO;
import com.ceiba.biblioteca.dto.LoginDTO;
import com.ceiba.biblioteca.dto.RegistroDTO;
import com.ceiba.biblioteca.modelo.Roles;
import com.ceiba.biblioteca.modelo.Usuarios;
import com.ceiba.biblioteca.repositorio.RolesRepositorio;
import com.ceiba.biblioteca.repositorio.UsuarioRepositorio;
import com.ceiba.biblioteca.seguridad.JwtGenerador;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/auth/")
public class AuthControlador {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepositorio rolesRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final JwtGenerador jwtGenerador;
    public AuthControlador(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RolesRepositorio rolesRepositorio,
                           UsuarioRepositorio usuarioRepositorio, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepositorio = rolesRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.jwtGenerador = jwtGenerador;
    }
    //Método para poder registrar usuarios con rol dependiendo del rol que se elija.
    @PostMapping("registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody RegistroDTO registroDTO){
        if (Boolean.TRUE.equals(usuarioRepositorio.existsByUsername(registroDTO.getUsername()))){
            return ResponseEntity.badRequest().body("El usuario ya existe, intenta con otro.");
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(registroDTO.getUsername());
        usuarios.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        Roles roles;
        if (registroDTO.getRol().equalsIgnoreCase("ADMIN")) {
            roles = rolesRepositorio.findByName("ADMIN")
                    .orElseThrow(() -> new NoSuchElementException("No se encontró el rol ADMIN"));
            return registrarUsuarioConRol(usuarios, roles, "Registro de usuario administrador exitoso.");
        } else {
            roles = rolesRepositorio.findByName("USER")
                    .orElseThrow(() -> new NoSuchElementException("No se encontró el rol USER"));
            return registrarUsuarioConRol(usuarios, roles, "Registro de usuario exitoso.");
        }
    }
    private ResponseEntity<String> registrarUsuarioConRol(Usuarios usuarios, Roles roles, String mensaje) {
        usuarios.setRoles(Collections.singletonList(roles));
        usuarioRepositorio.save(usuarios);
        return ResponseEntity.ok().body(mensaje);
    }
    //Método para poder loguear un usuario y obtener un token.
    @PostMapping("login")
    public ResponseEntity<AuthRespuestaDTO> login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        return new ResponseEntity<>(new AuthRespuestaDTO(token), HttpStatus.OK);
    }
}
