package com.example.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resultados {
    @JsonAlias("results")
    private List<DatosLibro> resultados;

    public List<DatosLibro> getResultados(){
        return resultados;
    }

    public void setResultados(List<DatosLibro> resultados) {
        this.resultados = resultados;
    }
}
