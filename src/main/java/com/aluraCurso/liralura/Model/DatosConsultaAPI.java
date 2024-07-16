package com.aluraCurso.liralura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosConsultaAPI(
        @JsonAlias("count") Integer numeroLibros,
        @JsonAlias("next") String pagProxima,
        @JsonAlias("previous") String pagAnterior,
        @JsonAlias("results") List<DatosLibro> resultado
) {
}