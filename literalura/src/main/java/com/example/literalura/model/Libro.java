package com.example.literalura.model;

import java.util.List;
import java.util.Set;

public class Libro {
    private String titulo;
    private Set<Autor> autores;
    private Integer descargas;
    private String idioma;

    public Libro(String titulo, String idiomas, Integer descargas, Set<Autor>autores) {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
