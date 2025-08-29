package io.github.ohhigordev.libaryapi.repository.Specs;

import io.github.ohhigordev.libaryapi.model.GeneroLivro;
import io.github.ohhigordev.libaryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;


public class LivroSpecs {

    public static Specification<Livro> isbnEquals(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    // O like é usado para digitarmos apenas uma parte de "titulo" e a busca ja conseguir ser feita;
    // Tanto o "cb.upper" como o "toUpperCase" são para deixar o titulo em caixa alta;
    public static Specification<Livro> tituloLike(String titulo){
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")),
                "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEquals(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEquals(Integer anoPublicacao){
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
        return (root, query, cb) ->
                cb.equal(cb.function("to_char",
                        String.class,
                        root.get("dataPublicacao"),
                        cb.literal("YYYY")),
                        anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) -> {
            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }

}
