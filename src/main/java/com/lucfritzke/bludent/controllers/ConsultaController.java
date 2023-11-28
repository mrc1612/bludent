package com.lucfritzke.bludent.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucfritzke.bludent.domain.Consulta;
import com.lucfritzke.bludent.dto.ConsultaDTO;
import com.lucfritzke.bludent.dto.ConsultaListaDTO;
import com.lucfritzke.bludent.dto.ConsultaPutDTO;
import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.services.ConsultaService;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {


    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/inserir")
    public ResponseEntity<Consulta> create(@Validated @RequestBody ConsultaDTO c){

        return ResponseEntity.ok().body(consultaService.create(c));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Consulta> update(@Validated @RequestBody ConsultaPutDTO c){
        return ResponseEntity.ok().body(consultaService.update(c));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ErroDTO> delete(@Validated @PathVariable Long id){
        return consultaService.delete(id);
    }
    

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Consulta> getConsultaById(@Validated @PathVariable Long id){

        return ResponseEntity.ok().body(consultaService.getById(id));

    }

    @GetMapping("buscar/")
    public ResponseEntity<List<ConsultaListaDTO>> getConsultasParam(
        @Validated @RequestParam(name = "data") LocalDate data,
        @RequestParam(name = "dentista") long idDentista
    ){
        return ResponseEntity.ok().body(consultaService.getConsultaParam(data, idDentista));
    }
   
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException de) {
        ErroDTO e = new ErroDTO(409, "Conflict", de.getMessage());
        return ResponseEntity.status(409).body(e);
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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> notFoundExpeption(NotFoundException ne){
        ErroDTO e = new ErroDTO(404, "Not found", ne.getMessage());
        return ResponseEntity.status(404).body(e);
    }
}
