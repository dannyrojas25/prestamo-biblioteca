package com.ceiba.biblioteca;

import com.ceiba.biblioteca.modelo.Libro;
import com.ceiba.biblioteca.modelo.LibroJSON;
import com.ceiba.biblioteca.repositorio.LibroRepositorio;
import com.ceiba.biblioteca.servicio.LibroServicio;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class LibroServicioTest {

    @Mock
    private LibroRepositorio libroRepositorio;
    @InjectMocks
    private LibroServicio libroServicio;
    public LibroServicioTest(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void guardarLibrosDesdeJSONTest(){
        LibroJSON libroJSON = LibroJSON.builder()
                .status("ok")
                .libros(Arrays.asList(
                        new Libro(1L, "Cien años de soledad", "Gabriel García Márquez", "https://images.cdn3.buscalibre.com/fit-in/360x360/61/8d/618d227e8967274cd9589a549adff52d.jpg"),
                        new Libro(2L, "El señor de los anillos (Trilogía)", "J. R. R. Tolkien", "https://images.cdn3.buscalibre.com/fit-in/360x360/54/49/5449ba87a3e457a22dd6d0972b5c261e.jpg")
                ))
                .build();

        when(libroRepositorio.existsByNombreAndAuthor("Cien años de soledad", "Gabriel García Márquez")).thenReturn(false);
        when(libroRepositorio.existsByNombreAndAuthor("El señor de los anillos (Trilogía)", "J. R. R. Tolkien")).thenReturn(false);

        ResponseEntity<String> responseEntity = libroServicio.guardarLibrosDesdeJSON(libroJSON);

        verify(libroRepositorio, times(1)).save(any());
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
        assert(responseEntity.getBody().equals("Se registraron exitosamente el/los libro(s)."));
    }
}
