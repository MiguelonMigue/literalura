package com.example.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO(
        String nombre,
        Integer anioDeNacimiento,
        Integer anioDeFallecimiento
) {
}
