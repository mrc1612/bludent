package com.lucfritzke.bludent.controllers;

import com.lucfritzke.bludent.domain.Procedimento;
import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.dto.StatusOkDTO;
import com.lucfritzke.bludent.services.ProcedimentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.lucfritzke.bludent.exceptions.DeleteException;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Procedimento", description = "Responsavel por gerencias os dados dos tipos de procedimentos")
@RestController
@RequestMapping("/procedimento")
@Validated
public class ProcedimentoController {

    @Autowired
    private ProcedimentoService service;

    @Operation(
        summary = "Inserir novo Procedimento",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for criado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(example = "{\"descricao\":\"TratamentodeCanal\"}"))),
            @ApiResponse(responseCode = "400", description = "Quando o registro for nulo ou houver formatação incorreta de dados"),
        }   
    )
    @PostMapping("/inserir")
    public ResponseEntity<Procedimento> createProcedimento(@Validated @RequestBody Procedimento procedimento) {
        return ResponseEntity.ok().body(service.create(procedimento));
    }


    @Operation(
        summary = "Obter dados por ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for encontrado"),
            @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado"),
        }   
    )
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Procedimento> getProcedimentoById(@PathVariable Long id) {
        Procedimento p = service.findById(id);
        return ResponseEntity.ok().body(p);
    }

    @Operation(summary = "Deletar um registro existente", responses = {
        @ApiResponse(responseCode = "200", description = "Quando o registro for deletado"), })
        @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProcedimento(@PathVariable Long id) {
        service.delete(id);
        StatusOkDTO s = new StatusOkDTO("OK", "Procedimento deletado com sucesso");
        return ResponseEntity.ok().body(s);
    }

    @Operation(
        summary = "Alterar dados existentes por ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for encontrado"),
            @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado"),
        }   
    )
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Procedimento> updateProcedimento(@PathVariable Long id, @RequestBody Procedimento procedimento) {
        Procedimento p = service.findById(id);
        p.setDescricao(procedimento.getDescricao());
        service.update(p, id);
        return ResponseEntity.ok().body(p);
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
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException de){
        return ResponseEntity.status(409).body(new ErroDTO("Conflict", "Procedimento esta sendo referenciado por outra entidade"));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> notFoundExpeption(NotFoundException ne){
        ErroDTO e = new ErroDTO("Not found", "Procedimento não encontrado");
        return ResponseEntity.status(404).body(e);
    }

    @ExceptionHandler(DeleteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> deleteException(DeleteException de){
        ErroDTO e = new ErroDTO("ERRO", "Procedimento não encontrado");
        return ResponseEntity.status(404).body(e);
    }
}