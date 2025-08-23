package io.github.ohhigordev.libaryapi.repository;

import io.github.ohhigordev.libaryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNascionalidade(String nascionalidade);
    List<Autor> findByNomeAndNascionalidade(String nome, String nascionalidade);

    // Método personalizado para buscar e validar no repositório
    Optional<Autor> findByNomeAndDataNascimentoAndNascionalidade(
            String nome, LocalDate dataNascimento, String nascionalidade
    );

}
