package com.aluraCurso.liralura.repository;



import com.aluraCurso.liralura.Model.CategoriaIdioma;
import com.aluraCurso.liralura.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findLibroBytitulo(String titulo);
    List<Libro> findLibrosByidioma(CategoriaIdioma idioma);


}