package io.github.ohhigordev.libaryapi.controller.dto.Livro;

import io.github.ohhigordev.libaryapi.controller.dto.Autor.AutorDTO;
import io.github.ohhigordev.libaryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(

        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        AutorDTO autorDTO

) {
}
