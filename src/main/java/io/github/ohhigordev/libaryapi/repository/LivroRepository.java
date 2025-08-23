package io.github.ohhigordev.libaryapi.repository;

import io.github.ohhigordev.libaryapi.model.Autor;
import io.github.ohhigordev.libaryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Implementando um Query Method:

    boolean existsByAutor(Autor autor);

    //select * from livro where id_autor = id;
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = titulo;
    List<Livro> findByTitulo(String titulo);

    List<Livro> findByisbn(String isbn);

    // Agora fazendo uma pesquisa utilizando dois parametros

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
}
