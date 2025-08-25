package io.github.ohhigordev.libaryapi.controller;

import io.github.ohhigordev.libaryapi.Exception.RegistroDuplicadoException;
import io.github.ohhigordev.libaryapi.controller.dto.ErroResposta;
import io.github.ohhigordev.libaryapi.controller.dto.Livro.CadastroLivroDTO;
import io.github.ohhigordev.libaryapi.controller.mappers.LivroMapper;
import io.github.ohhigordev.libaryapi.model.Livro;
import io.github.ohhigordev.libaryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try{
            Livro livro = mapper.toEntity(dto);
            service.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
        }catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}
