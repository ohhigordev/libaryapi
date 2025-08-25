package io.github.ohhigordev.libaryapi.controller.mappers;

import io.github.ohhigordev.libaryapi.controller.dto.Livro.CadastroLivroDTO;
import io.github.ohhigordev.libaryapi.model.Livro;
import io.github.ohhigordev.libaryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);
}
