package com.lucfritzke.bludent.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.repositories.DentistaRepository;
import com.lucfritzke.bludent.services.DentistaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Dentista", description = "Responsavel por gerencias os dados de cadastro dos dentista")
@RequestMapping("/dentista")
@RestController
public class DentistaController {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private DentistaService dentistaService;

    @Operation(
        summary = "Inserir novo registro",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for criado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(example = "{\"nome\": \"Marcos da Silva\", \"registro\": \"123456\" }"))),
            @ApiResponse(responseCode = "400", description = "Quando o registro for nulo")
        }
        
    )
    @PostMapping
    public ResponseEntity getDentista(
        @Valid @RequestBody Dentista entity, 
        BindingResult result
    ){
        // Lógica para lidar com os resultados de validação e processamento dos dados
        Optional<Dentista> o;
        if (result.hasErrors()) {
            // Se houver erros de validação, você pode acessar essas mensagens aqui
            List<ObjectError> errors = result.getAllErrors();
            // Faça algo com as mensagens de erro, como retornar uma resposta com detalhes dos erros
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    
        try {
            // Código para salvar a entidade no banco de dados ou realizar outras operações
            return ResponseEntity.ok().body(entity);
        } catch (Exception e) {
            // Lidar com outras exceções
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Dentista>> getDEntityByNome(@PathVariable String nome) {
        List<Dentista> liDen = dentistaRepository.findByNomeContaining(nome);
        if (liDen.isEmpty()) {
            throw new NotFoundException("dentista nao localizado");
        } else {
            return ResponseEntity.ok().body(liDen);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dentista> updateDentista(@PathVariable Long id, @RequestBody Dentista updatedDentista) {
        Optional<Dentista> existingDentistaOptional = dentistaRepository.findById(id);

        if (existingDentistaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Dentista existingDentista = existingDentistaOptional.get();

        existingDentista.setNome(updatedDentista.getNome());
        existingDentista.setRegistro(updatedDentista.getRegistro());
        existingDentista.setEmail(updatedDentista.getEmail());

        Dentista updated = dentistaRepository.save(existingDentista);

        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDentista(@PathVariable Long id) {
        Optional<Dentista> dentista = dentistaRepository.findById(id);
        if (dentista.isPresent()) {
            dentistaRepository.deleteById(id);
            String json = "{ \"status\" : \"OK\", \"mensagem\" : \"OK\"}";
            return ResponseEntity.ok().body(json);
        } else {
            String errorJson = "{ \"status\" : \"ERRO\", \"mensagem\" : \"Código de Dentista não existe\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJson);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> getDentista(@PathVariable Long id) {
        Dentista d = dentistaService.findById(id);
        return ResponseEntity.ok().body(d);

    }

}
