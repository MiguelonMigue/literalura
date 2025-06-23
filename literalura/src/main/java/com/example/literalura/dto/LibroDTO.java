package com.example.literalura.dto;

import java.util.List;
import java.util.Set;

public record LibroDTO(
        String titulo,
        Set<AutorDTO> autores,
        String idiomas,
        int descargas

) {}