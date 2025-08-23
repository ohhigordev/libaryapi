package io.github.ohhigordev.libaryapi.repository;

import io.github.ohhigordev.libaryapi.model.Autor;
import io.github.ohhigordev.libaryapi.model.GeneroLivro;
import io.github.ohhigordev.libaryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test // Salvando sem efeito cascata
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("763247-89723");
        livro.setPreco(BigDecimal.valueOf(45));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("As cronicas de Narnia");
        livro.setDataPublicacao(LocalDate.of(1999,6,15));

        // Associar esse livro a um autor já existente no banco:
        Autor autor = autorRepository.
                findById(UUID.fromString("fc583b77-2c4a-4441-8c7c-78172b007177"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test // Salvando com o efeito cascata
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("763247-9834523");
        livro.setPreco(BigDecimal.valueOf(45));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1999,6,15));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNascionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1988,3,20));


        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutoDoLivroTest(){
        UUID id = UUID.fromString("79a99bf1-fc4e-432d-a386-9c063295f1cd");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        // Associar esse determinado livro a um novo autor
        UUID uuid = UUID.fromString("e810c197-23de-489c-8d74-2e3ea96c5f42");
        Autor maria = autorRepository.findById(uuid).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);

    }

    // Como buscar livro e trazer o autor junto
    @Test
    void buscarLivroTest(){
        UUID id = UUID.fromString("79a99bf1-fc4e-432d-a386-9c063295f1cd");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("Harry Potter - A pedra filosofal");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorISBNTest(){
        List<Livro> lista = repository.findByisbn("763247-89723");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloEPreco(){
        var preco = BigDecimal.valueOf(45.00);
        var tituloPesquisa = "As cronicas de Narnia";
        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }
}