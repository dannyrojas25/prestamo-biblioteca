package com.ceiba.biblioteca.servicio;

import com.ceiba.biblioteca.modelo.*;
import com.ceiba.biblioteca.repositorio.LibroRepositorio;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroServicio {
    private LibroRepositorio libroRepositorio;

    public LibroServicio(LibroRepositorio libroRepositorio) {
        this.libroRepositorio = libroRepositorio;
    }

    public ResponseEntity<String> guardarLibrosDesdeJSON(LibroJSON libroJSON) {
        try {
            boolean algunLibroRegistrado = false;
            boolean todosLibrosRegistrados = true;
            List<String> librosRegistrados = new ArrayList<>();
            List<String> librosNoRegistrados = new ArrayList<>();

            for (var detalle : libroJSON.getLibros()) {
                boolean libroExistente = libroRepositorio.existsByNombreAndAuthor(detalle.getNombre(), detalle.getAuthor());
                if (!libroExistente) {
                    Libro libro = Libro.builder()
                            .nombre(detalle.getNombre())
                            .author(detalle.getAuthor())
                            .foto(detalle.getFoto())
                            .build();
                    libroRepositorio.save(libro);
                    algunLibroRegistrado = true;
                    todosLibrosRegistrados = false;
                    librosRegistrados.add(detalle.getNombre());
                } else {
                    librosNoRegistrados.add(detalle.getNombre());
                }
            }

            if (todosLibrosRegistrados) {
                return new ResponseEntity<>("Todos los libros de la lista ya están registrados en la base de datos.", HttpStatus.OK);
            } else if (algunLibroRegistrado) {
                if (librosNoRegistrados.isEmpty()) {
                    return new ResponseEntity<>("Se registraron exitosamente el/los libro(s) de la lista.", HttpStatus.OK);
                } else {
                    String mensajeLibrosRegistrados = String.join(", ", librosRegistrados);
                    return new ResponseEntity<>("El/los libro(s): '" + mensajeLibrosRegistrados + "' se registraron con éxito.", HttpStatus.OK);
                }
            } else {
                String mensajeLibrosNoRegistrados = String.join(", ", librosNoRegistrados);
                return new ResponseEntity<>("El/los libro(s) fueron registrados con éxito en la base de datos, a excepción de este(os) libro(s): '" + mensajeLibrosNoRegistrados + "'.", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
