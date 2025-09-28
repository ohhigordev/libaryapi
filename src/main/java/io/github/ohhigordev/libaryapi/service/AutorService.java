package io.github.ohhigordev.libaryapi.service;

import io.github.ohhigordev.libaryapi.Exception.OperacaoNaoPermitidaException;
import io.github.ohhigordev.libaryapi.Security.SecurityService;
import io.github.ohhigordev.libaryapi.model.Autor;
import io.github.ohhigordev.libaryapi.model.Usuario;
import io.github.ohhigordev.libaryapi.repository.AutorRepository;
import io.github.ohhigordev.libaryapi.repository.LivroRepository;
import io.github.ohhigordev.libaryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;


    public Autor salvar(Autor autor){
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já estaja na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor possui livros cadastrados");
        }
        repository.delete(autor);
    }

    // Refatorando o metodo de pesquisar (pesquisa dinamica)
    public List<Autor> pesquisar(String nome, String nascionalidade){
        if(nome != null && nascionalidade != null){
            return repository.findByNomeAndNascionalidade(nome, nascionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nascionalidade != null){
            return repository.findByNascionalidade(nascionalidade);
        }

        return repository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nascionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNascionalidade(nascionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues() // Ignora valores nulos
                .withIgnoreCase() // Ignora letras maiusculas ou minusculas
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}
