package com.example.literalura.model;

import com.example.literalura.dto.LibroDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private List <String> idiomas;
    private Integer descargas;

    public Libro(LibroDTO dto) {
        this.titulo = dto.titulo();
        this.idiomas = dto.idiomas();
        this.descargas = dto.descargas();
    }

    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

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

    public List <String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idiomas + '\'' +
                ", descargas=" + descargas +
                ", autor=" + autor +
                '}';
    }
}
