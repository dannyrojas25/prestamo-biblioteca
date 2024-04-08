package com.ceiba.biblioteca.dto;

import lombok.Data;

//Es el encargado de devolvernos la imformación con el token y el tipo que tenga este.
@Data
public class AuthRespuestaDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthRespuestaDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
