package io.github.ohhigordev.libaryapi.controller.dto.Autor;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        // Adicionando apenas os dados que nos queremos receber da classe Autor
        UUID id,
        @NotBlank(message = "Campo obrigatório") // A String não pode vir nula e nem vazia;
        @Size(min = 2,max = 100, message = "Campo fora do tamanho padrão")
        String nome,
        @NotNull(message = "Campo obrigatório") // Esse campo não pode vir nulo;
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2,max = 50, message = "Campo fora do tamanho padrão")
        String nascionalidade
) {}
