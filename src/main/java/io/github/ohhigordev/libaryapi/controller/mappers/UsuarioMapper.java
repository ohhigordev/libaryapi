package io.github.ohhigordev.libaryapi.controller.mappers;

import io.github.ohhigordev.libaryapi.controller.dto.Usuario.UsuarioDTO;
import io.github.ohhigordev.libaryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

}
