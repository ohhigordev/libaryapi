package io.github.ohhigordev.libaryapi.controller.dto.Usuario;

import java.util.List;

public record UsuarioDTO(
        String login,
        String senha,
        List<String> roles

) {
}
