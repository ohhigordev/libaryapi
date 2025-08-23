package io.github.ohhigordev.libaryapi.controller.mappers;

import io.github.ohhigordev.libaryapi.controller.dto.Autor.AutorDTO;
import io.github.ohhigordev.libaryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome",target = "nome")
    @Mapping(source = "dataNascimento",target = "dataNascimento")
    @Mapping(source = "nascionalidade",target = "nascionalidade")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);

}
