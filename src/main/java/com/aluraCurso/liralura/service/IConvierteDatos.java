package com.aluraCurso.liralura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}