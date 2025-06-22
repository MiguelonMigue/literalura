package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;
    private List <String> idiomas;
    private Integer numeroDescargas;

    public Libro() {
    }

    public Libro(Long id, String titulo, List<String> idiomas, List<Autor> autores, Integer numeroDescargas) {
        this.id = id;
        this.titulo = titulo;
        this.idiomas = idiomas;
        this.autores = autores;
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString(){
        return "Título: " + titulo +
                "Autores: " + autores +
                "Idiomas: " + idiomas +
                "Número de descargas: " + numeroDescargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }


}
