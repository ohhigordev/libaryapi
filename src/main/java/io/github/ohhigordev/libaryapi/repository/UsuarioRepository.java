package io.github.ohhigordev.libaryapi.repository;

import io.github.ohhigordev.libaryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByLogin(String login);

    Usuario findByEmail(String email);
}
