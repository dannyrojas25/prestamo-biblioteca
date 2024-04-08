package com.ceiba.biblioteca.repositorio;

import com.ceiba.biblioteca.modelo.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByUsername(String username);

    Boolean existsByUsername(String username);
}