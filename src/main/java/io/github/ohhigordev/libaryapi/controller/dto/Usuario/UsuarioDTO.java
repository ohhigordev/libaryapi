package io.github.ohhigordev.libaryapi.controller.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "campo obrigatório")
        String login,
        @Email(message = "Email invalido")
        @NotBlank(message = "campo obrigatório")
        String email,
        @NotBlank(message = "campo obrigatório")
        String senha,
        List<String> roles

) {
}
