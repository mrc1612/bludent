package com.lucfritzke.bludent.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
import com.lucfritzke.bludent.domain.Paciente;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.services.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Paciente", description = "Responsavel por gerencias os dados de cadastro dos pacientes")
@RequestMapping("/paciente")
@Validated
@RestController
public class PacienteController {

    @Autowired
    private PacienteService service;
    
    @Operation(
        summary = "Inserir novo paciente",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for criado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(example = "{\"nome\":\"Lucas Fritzke\",\"dataNascimento\":\"2000-12-25\",\"cpf\":\"18219822821\",\"telefone\":\"47952635878\"}"))),
            @ApiResponse(responseCode = "400", description = "Quando o registro for nulo ou houver formatação incorreta de dados"),
            @ApiResponse(responseCode = "409", description = "Quando já existir usuário com cpf no sistema")
        }   
    )
    @PostMapping("/cadastrar")
    public ResponseEntity<Paciente> create(@Valid @RequestBody Paciente entity){

        return ResponseEntity.ok().body(service.create(entity));
        
    }

    @Operation(
        summary = "Obter dados por ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Quando o registro for encontrado"),
            @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado"),
        }   
    )
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getDentista(
            @Parameter(description = "ID do registro a ser obtido", required = true)
            @PathVariable Long id) {
        Paciente p = service.findById(id);
        return ResponseEntity.ok().body(p);
    }


    @Operation(summary = "Deletar um registro existente", responses = {
        @ApiResponse(responseCode = "200", description = "Quando o registro for deletado"),
        @ApiResponse(responseCode = "404", description = "Quando o registro não for encontrado")})
    @DeleteMapping("/{id}")
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
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(@PathVariable Long id, @RequestBody Paciente entity) {
        Paciente paciente = service.findById(id);
        paciente.setNome(entity.getNome());
        paciente.setDataNascimento(entity.getDataNascimento());
        paciente.setCpf(entity.getCpf());
        paciente.setTelefone(entity.getTelefone());
        service.update(entity, id);

        return ResponseEntity.ok().body(entity);
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

        return ResponseEntity.status(409).body(de.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> notFoundExpeption(NotFoundException ne){

        return ResponseEntity.status(404).body(ne.getMessage());
    }
}
