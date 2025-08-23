package io.github.ohhigordev.libaryapi.validator;

import io.github.ohhigordev.libaryapi.Exception.RegistroDuplicadoException;
import io.github.ohhigordev.libaryapi.model.Autor;
import io.github.ohhigordev.libaryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    //Método auxiliar
    private Boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNascionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNascionalidade()
        );

        // Lógica de primeiro cadastro de autor:
        if(autor.getId() == null){
            return autorEncontrado.isPresent();
        }

        // Lógica de atualização de autor já cadastrado:
        return autor.getId().equals(autorEncontrado.get().getId())  && autorEncontrado.isPresent();
    }
}
