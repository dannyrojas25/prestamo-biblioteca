package com.ceiba.biblioteca.repositorio;

import com.ceiba.biblioteca.modelo.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepositorio extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String nombre);
}
