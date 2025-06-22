package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosAutor{
        @JsonAlias("name")
        private String nombre;
        @JsonAlias("birth_year")
        private Integer nacimiento;
        @JsonAlias("death_year")
        private Integer fallecimiento;

        public String getNombre() {
                return nombre;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public Integer getNacimiento() {
                return nacimiento;
        }

        public void setNacimiento(Integer nacimiento) {
                this.nacimiento = nacimiento;
        }

        public Integer getFallecimiento() {
                return fallecimiento;
        }

        public void setFallecimiento(Integer fallecimiento) {
                this.fallecimiento = fallecimiento;
        }
}
