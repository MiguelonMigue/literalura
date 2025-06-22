package com.example.literalura.dto;

import com.example.literalura.model.Autor;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record LibroDTO(
    String titulo,
    List<Autor> autores,
    List <String> idiomas,
    Integer descargas


        // Puedes mapear autor más adelante
){


}
