package com.ceiba.biblioteca.seguridad;

import com.ceiba.biblioteca.modelo.Roles;
import com.ceiba.biblioteca.modelo.Usuarios;
import com.ceiba.biblioteca.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public CustomUserDetailsService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    //Método para traernos la lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAutorithies(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //Método para traernos un usuario con todos sus datos por medio de sus usernames
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = usuarioRepositorio.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usario no encontrado."));
        return new User(usuarios.getUsername(), usuarios.getPassword(), mapToAutorithies(usuarios.getRoles()));
    }
}
