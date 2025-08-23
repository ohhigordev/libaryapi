package io.github.ohhigordev.libaryapi.repository;

import io.github.ohhigordev.libaryapi.model.Autor;
import io.github.ohhigordev.libaryapi.model.GeneroLivro;
import io.github.ohhigordev.libaryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
     AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salverTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNascionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1951,8,5));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("9d74e423-4b85-4216-8c35-d8adfa208ee3");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    // Aqui deletaremos com base no id informado pela aplicação
    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("9d74e423-4b85-4216-8c35-d8adfa208ee3");
        repository.deleteById(id);
    }

    // Aqui deletaremos o objeto
    @Test
    public void deleteTest(){
        var id = UUID.fromString("e810c197-23de-489c-8d74-2e3ea96c5f42");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    // Adicionando autor com sua lista de livros
    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNascionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1970,2,22));

        // Adicionando livros a esse autor
        Livro livro1 = new Livro();
        livro1.setIsbn("7352-89723");
        livro1.setPreco(BigDecimal.valueOf(57));
        livro1.setGenero(GeneroLivro.ROMANCE);
        livro1.setTitulo("O meu principe encantado");
        livro1.setDataPublicacao(LocalDate.of(2000,5,12));
        livro1.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("7352-893453");
        livro2.setPreco(BigDecimal.valueOf(65));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O assassino do jaleco branco");
        livro2.setDataPublicacao(LocalDate.of(2002,11,13));
        livro2.setAutor(autor);

        // Adicionando livros a nossa lista
        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro1);
        autor.getLivros().add(livro2);

        // Vamos agora salvar nosso autor e livros no nosso banco de dados
        repository.save(autor);

    }
    @Test
    void listarLivroAutor(){
        UUID id = UUID.fromString("fc583b77-2c4a-4441-8c7c-78172b007177");
        Autor autor = repository.findById(id).get();

        // Buscando os livros do autor:
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
