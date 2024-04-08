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

@Controller
@RequestMapping("/api/auth/")
public class AuthControlador {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RolesRepositorio rolesRepositorio;
    private UsuarioRepositorio usuarioRepositorio;
    private JwtGenerador jwtGenerador;

    public AuthControlador(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RolesRepositorio rolesRepositorio,
                           UsuarioRepositorio usuarioRepositorio, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepositorio = rolesRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.jwtGenerador = jwtGenerador;
    }

    //Método para poder registrar usuarios con rol USER.
    @PostMapping("registro")
    public ResponseEntity<String> registrar(@RequestBody RegistroDTO registroDTO){
        if (usuarioRepositorio.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("El usuario ya existe, intenta con otro.", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(registroDTO.getUsername());
        usuarios.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        Roles roles = rolesRepositorio.findByName("USER").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuarioRepositorio.save(usuarios);
        return new ResponseEntity<>("Registro de usuario exitoso.", HttpStatus.OK);
    }

    //Método para poder registrar usuarios con rol ADMIN.
    @PostMapping("registroAdm")
    public ResponseEntity<String> registrarAdmin (@RequestBody RegistroDTO registroDTO){
        if (usuarioRepositorio.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("El usuario ya existe, intenta con otro.", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(registroDTO.getUsername());
        usuarios.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        Roles roles = rolesRepositorio.findByName("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuarioRepositorio.save(usuarios);
        return new ResponseEntity<>("Registro de usuario administrador exitoso.", HttpStatus.OK);
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
