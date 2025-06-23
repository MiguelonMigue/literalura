package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibro {
        @JsonAlias("title")
        private String titulo;
        @JsonAlias("authors")
        private List<DatosAutor> autores;
        @JsonAlias("languages")
        private String idiomas;
        @JsonAlias("download_count")
        private Integer descargas;

        public String getTitulo() {
                return titulo;
        }

        public void setTitulo(String titulo) {
                this.titulo = titulo;
        }

        public List<DatosAutor> getAutores() {
                return autores;
        }

        public void setAutores(List<DatosAutor> autores) {
                this.autores = autores;
        }

        public String getIdiomas() {
                return idiomas;
        }

        public void setIdiomas(String idiomas) {
                this.idiomas = idiomas;
        }

        public Integer getDescargas() {
                return descargas;
        }

        public void setNumeroDescargas(Integer descargas) {
                this.descargas = descargas;
        }
}
