package com.lucfritzke.bludent.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.domain.Procedimento;
import com.lucfritzke.bludent.domain.ProcedimentoDentista;
import com.lucfritzke.bludent.domain.ProcedimentoDentistaId;
import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.dto.ProcedimentoDentistaDTO;
import com.lucfritzke.bludent.dto.ProcedimentoPorDentistaDTO;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.repositories.ProcedimentoDentistaRepository;
import com.lucfritzke.bludent.services.DentistaService;
import com.lucfritzke.bludent.services.ProcedimentoDentistaService;
import com.lucfritzke.bludent.services.ProcedimentoService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Atribuir a um Dentista um novo procedimento")
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
    @Operation(summary = "Buscar todos procedimentos disponiveis")
    @GetMapping("/listarProcedimentos")
    public ResponseEntity<List<Procedimento>> getListaProcedimentos() {
        List<Procedimento> procedimentos = service.getProcedimento();
        return ResponseEntity.ok(procedimentos);

    }
    @Operation(summary = "Buscar procedimentos realizados por dentistas")
    @GetMapping("/buscarPorDentista/{id}")
    public ResponseEntity<ProcedimentoPorDentistaDTO> getListaProcedimentos(@Valid @PathVariable Long id) {

        return ResponseEntity.ok(service.listarProcedimentosPorDentista(id));

    }

    @Operation(summary = "Atualizar procedimentos realizados por dentistas")
    @PutMapping("/")
    public ResponseEntity<ProcedimentoDentista> updateProcedimentoDentista(
            @Validated @RequestBody ProcedimentoDentistaDTO pDTO) {

        ProcedimentoDentistaId pdID = new ProcedimentoDentistaId(pDTO.getDentista(), pDTO.getProcedimento());
        ProcedimentoDentista pd = service.findById(pdID);
        if (pDTO.getDentista() != 0 && pDTO.getDentista() != pd.getDentista().getId()) {
            pd.getDentista().setId(pDTO.getDentista());
        }
        if (pDTO.getProcedimento() != 0 && pDTO.getProcedimento() != pd.getProcedimento().getId()) {
            pd.getProcedimento().setId(pDTO.getProcedimento());
        }
        if (pDTO.getValor() != 0 && pDTO.getValor() != pd.getValor()) {
            pd.setValor(pDTO.getValor());

        }

        return ResponseEntity.ok().body(service.update(pd));
    }
    @Operation(summary = "Deletar procedimentos realizados por dentistas")
    @DeleteMapping("/delete/")
    public ResponseEntity<?> deleteProcedimento(
            @RequestParam("d") Long dentistaId,
        @RequestParam("p") Long procedimentoId) {
        ProcedimentoDentistaId pID = new ProcedimentoDentistaId(dentistaId, procedimentoId);
        service.delete(pID);
        String json = "{ \"status\" : \"OK\", \"mensagem\" : \"OK\"}";
        return ResponseEntity.ok().body(json);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)

    public ResponseEntity<?> dataIntegrityViolationException(DataIntegrityViolationException de) {
        ErroDTO e = new ErroDTO(409, "Conflict", de.getMessage());
        return ResponseEntity.status(409).body(e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> notFoundExpeption(NotFoundException ne) {
        if(ne.getMessage().contains("ERRO")){
            return ResponseEntity.status(404).body(ne.getMessage());
        }
        ErroDTO e = new ErroDTO(404, "Not Found", ne.getMessage());
        return ResponseEntity.status(404).body(e);
    }

}
