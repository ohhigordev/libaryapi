package io.github.ohhigordev.libaryapi.controller;

import io.github.ohhigordev.libaryapi.Exception.OperacaoNaoPermitidaException;
import io.github.ohhigordev.libaryapi.Exception.RegistroDuplicadoException;
import io.github.ohhigordev.libaryapi.controller.dto.Autor.AutorDTO;
import io.github.ohhigordev.libaryapi.controller.dto.ErroResposta;
import io.github.ohhigordev.libaryapi.controller.mappers.AutorMapper;
import io.github.ohhigordev.libaryapi.model.Autor;
import io.github.ohhigordev.libaryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
//http://localhost:8080/autores
public class AutorController {

    private final AutorService service;
    private final AutorMapper mapper;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto){
        try {
            Autor autor = mapper.toEntity(dto);
            service.salvar(autor);

            //http://localhost:8080/autores/e810c197-23de-489c-8d74-2e3ea96c5f42
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e){
            ErroResposta erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);

        return service.
                obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDto(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {


            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());

            return ResponseEntity.noContent().build();
        }catch (OperacaoNaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nascionalidade", required = false) String nascionalidade
    ){
        List<Autor> resultado =  service.pesquisaByExample(nome, nascionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @Valid
            @PathVariable("id") String id,
            @RequestBody AutorDTO dto
    ){
        try {
            UUID idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNascionalidade(dto.nascionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        }catch (RegistroDuplicadoException e){
            ErroResposta erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

}
