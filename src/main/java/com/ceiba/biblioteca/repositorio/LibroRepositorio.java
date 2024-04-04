package com.ceiba.biblioteca.repositorio;

import com.ceiba.biblioteca.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
}
