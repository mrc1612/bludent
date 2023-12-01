package com.lucfritzke.bludent.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.services.DentistaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private DentistaService service;
    
    @Operation(
        summary = "Inserir novo Dentista",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for criado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(example = "{\"nome\":\"Julio Farias\",\"registro\":789,\"email\":\"julia@hotmail.com\"}"))),
            @ApiResponse(responseCode = "400", description = "Quando o registro for nulo ou houver formatação incorreta de dados"),
        }   
    )
    @PostMapping("/inserir")
    public ResponseEntity<Dentista> create(@Valid @RequestBody Dentista entity){

        return ResponseEntity.ok().body(service.create(entity));
        
    }

    @Operation(
        summary = "Obter dados por ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for encontrado"),
            @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado"),
        }   
    )
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Dentista> getDentista(
            @Parameter(description = "ID do registro a ser obtido", required = true)
            @PathVariable Long id) {
        Dentista p = service.findById(id);
        return ResponseEntity.ok().body(p);
    }


    @Operation(summary = "Deletar um registro existente", responses = {
        @ApiResponse(responseCode = "200", description = "Quando o registro for deletado"),
        @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado") })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDentista(
            @Parameter(description = "ID do registro a ser deletado", required = true)
            @PathVariable Long id) {
        service.findByIdDelete(id);
        service.delete(id);
        String json = "{ \"status\" : \"OK\", \"mensagem\" : \"OK\"}";
        return ResponseEntity.ok().body(json);
    }

     @Operation(
        summary = "Alterar dados existentes por ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for encontrado"),
            @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado"),
        }   
    )
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPaciente(@PathVariable Long id, @RequestBody Dentista entity) {
        Dentista dentista = service.findById(id);

        if(entity.getNome() == null && entity.getRegistro() == 0 && entity.getEmail() == null){
            return ResponseEntity.status(400).body(new ErroDTO("ERRO", "Dados invalidos"));
        }
        if(entity.getNome() != null){
             dentista.setNome(entity.getNome());
        }
        if(entity.getRegistro() != 0){
            dentista.setRegistro(entity.getRegistro());
        }
        if(entity.getEmail() != null){
            dentista.setEmail(entity.getEmail());
        }
        service.update(dentista, id);
        return ResponseEntity.ok().body(dentista);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException de) {
        ErroDTO e = new ErroDTO("ERRO", de.getMessage());
        return ResponseEntity.status(409).body(e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> notFoundExpeption(NotFoundException ne){

        return ResponseEntity.status(404).body(new ErroDTO("ERRO", "Dentista não encontrado"));
    }

}
