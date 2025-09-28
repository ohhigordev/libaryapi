package io.github.ohhigordev.libaryapi.service;

import io.github.ohhigordev.libaryapi.Security.SecurityService;
import io.github.ohhigordev.libaryapi.model.GeneroLivro;
import io.github.ohhigordev.libaryapi.model.Livro;
import io.github.ohhigordev.libaryapi.model.Usuario;
import io.github.ohhigordev.libaryapi.repository.LivroRepository;
import io.github.ohhigordev.libaryapi.repository.Specs.LivroSpecs;
import io.github.ohhigordev.libaryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static io.github.ohhigordev.libaryapi.repository.Specs.LivroSpecs.*;
import static org.springframework.data.jpa.domain.Specification.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;


    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro){
        repository.delete(livro);
    }

    public Page<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina
    ){
        // select * from livro where isbn = :isbn and nomeAutor =
//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEquals(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero));

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where(
                (root, query, cb) -> cb.conjunction());

        if(isbn != null){
            //query = query and isbn= :isbn
            specs = specs.and(isbnEquals(isbn));
        }

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if(genero != null){
            specs = specs.and(generoEquals(genero));
        }

        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEquals(anoPublicacao));
        }

        if(nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já estaja na base.");
        }

        validator.validar(livro);
        repository.save(livro);
    }
}
