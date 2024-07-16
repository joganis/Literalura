package com.aluraCurso.liralura.Model;

import java.util.Map;
import java.util.HashMap;

public enum CategoriaIdioma {
    ESPANOL("[es]", "Español"),
    INGLES("[en]", "Ingles"),
    FRANCES("[fr]", "Frances"),
    PORTUGUES("[pt]", "Portugues");

    private String categoriaGutendex;
    private String categoriaEspanol;

    private static final Map<String, CategoriaIdioma> GUTENDEX_MAP = new HashMap<>();
    private static final Map<String, CategoriaIdioma> ESPANOL_MAP = new HashMap<>();

    static {
        for (CategoriaIdioma categoria : CategoriaIdioma.values()) {
            GUTENDEX_MAP.put(categoria.categoriaGutendex.toLowerCase(), categoria);
            ESPANOL_MAP.put(categoria.categoriaEspanol.toLowerCase(), categoria);
        }
    }

    CategoriaIdioma(String categoriaGutendex, String categoriaEspanol) {
        this.categoriaGutendex = categoriaGutendex;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static CategoriaIdioma fromString(String text) {
        CategoriaIdioma categoria = GUTENDEX_MAP.get(text.toLowerCase());
        if (categoria == null) {
            throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
        }
        return categoria;
    }

    public static CategoriaIdioma fromEspanol(String text) {
        CategoriaIdioma categoria = ESPANOL_MAP.get(text.toLowerCase());
        if (categoria == null) {
            throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
        }
        return categoria;
    }
}