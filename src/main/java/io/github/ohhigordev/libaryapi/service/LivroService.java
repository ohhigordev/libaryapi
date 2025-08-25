package io.github.ohhigordev.libaryapi.service;

import io.github.ohhigordev.libaryapi.model.Livro;
import io.github.ohhigordev.libaryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;


    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }
}
