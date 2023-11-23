package com.lucfritzke.bludent.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.domain.Procedimento;
import com.lucfritzke.bludent.domain.ProcedimentoDentista;
import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.dto.ProcedimentoDentistaDTO;
import com.lucfritzke.bludent.dto.ProcedimentoPorDentistaDTO;
import com.lucfritzke.bludent.repositories.ProcedimentoDentistaRepository;
import com.lucfritzke.bludent.services.DentistaService;
import com.lucfritzke.bludent.services.ProcedimentoDentistaService;
import com.lucfritzke.bludent.services.ProcedimentoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Procedimento Dentista", description = "Responsavel por gerenciar os dados dos tipos de procedimentos realizados por cada dentista")
@RestController
@RequestMapping("/procedimentoDentista")
@Validated
public class ProcedimentoDentistaController {
    @Autowired
    private ProcedimentoService pService;

    @Autowired
    private DentistaService dService;

    @Autowired
    private ProcedimentoDentistaService service;

    @Autowired
    private ProcedimentoDentistaRepository repository;

    @PostMapping("/cadastrar")
    public ResponseEntity<ProcedimentoDentista> createProcedimento(
            @Validated @RequestBody ProcedimentoDentistaDTO pDTO) {

        Dentista d = dService.findById(pDTO.getDentista());
        Procedimento p = pService.findById(pDTO.getProcedimento());

        ProcedimentoDentista pd = new ProcedimentoDentista(d, p, pDTO.getValor());
        Optional<ProcedimentoDentista> check = repository.findById(pd.getId());

        if (check.isPresent() && check.get().equals(pd)) {
            throw new DataIntegrityViolationException("Dentista já possui procedimento cadastrado");
        }
        return ResponseEntity.ok().body(service.create(pd));
    }

    @GetMapping("/listarProcedimentos")
    public ResponseEntity<List<Procedimento>> getListaProcedimentos() {
        List<Procedimento> procedimentos = service.getProcedimento();
        return ResponseEntity.ok(procedimentos);

    }

    @GetMapping("/buscarPorDentista/{id}")
    public ResponseEntity<ProcedimentoPorDentistaDTO> getListaProcedimentos(@Valid @PathVariable Long id) {

        return ResponseEntity.ok(service.listarProcedimentosPorDentista(id));

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException de) {
        ErroDTO e = new ErroDTO(409, "Conflict", de.getMessage());
        return ResponseEntity.status(409).body(e);
    }

}
