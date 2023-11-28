package com.lucfritzke.bludent.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Consulta;
import com.lucfritzke.bludent.domain.ProcedimentoDentista;
import com.lucfritzke.bludent.domain.ProcedimentoDentistaId;
import com.lucfritzke.bludent.dto.ConsultaDTO;
import com.lucfritzke.bludent.dto.ConsultaListaDTO;
import com.lucfritzke.bludent.dto.ConsultaPutDTO;
import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.repositories.ConsultaRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProcedimentoDentistaService procedimentoDentistaService;

    public Consulta create(ConsultaDTO c) {

        Consulta consulta = new Consulta();
        ProcedimentoDentistaId pdID = new ProcedimentoDentistaId(c.getIdDentista(), c.getIdProcedimento());
        consulta.setProcedimentoDentista(procedimentoDentistaService.findById(pdID));
        consulta.setPaciente(pacienteService.findById(c.getIdPaciente()));
        consulta.setData(c.getData());
        consulta.setHorario(c.getHorario());
        try {
            return consultaRepository.save(consulta);
        } catch (DataIntegrityViolationException de) {
            throw new DataIntegrityViolationException("Dentista já possui paciente nesse horário");
        }

    }

    public Consulta update(ConsultaPutDTO c) {

        Optional<Consulta> o = consultaRepository.findById(c.id());
        if (!o.isPresent()) {
            throw new NotFoundException("Conulsta nao encontrada");
        }
        ProcedimentoDentista pd = procedimentoDentistaService
                .findById(new ProcedimentoDentistaId(c.idDentista(), c.idProcedimento()));
        Consulta consulta = o.get();
        consulta.setHorario(c.horario());
        consulta.setData(c.data());
        consulta.setPaciente(pacienteService.findById(c.idPaciente()));
        consulta.setProcedimentoDentista(pd);
        return consultaRepository.save(consulta);
    }

    public ResponseEntity<ErroDTO> delete(Long id) {
        Optional<Consulta> o = consultaRepository.findById(id);
        if (o.isPresent()) {
            consultaRepository.delete(o.get());
            return ResponseEntity.status(200).body(new ErroDTO(200, "OK", "OK"));
        } else {
            return ResponseEntity.status(404).body(new ErroDTO(404, "ERRO", "Id da consulta não localizado"));
        }

    }

    public Consulta getById(Long id) {
        return consultaRepository.findById(id).orElseThrow(()-> new NotFoundException("Consulta não existe"));
    }

    public List<ConsultaListaDTO> getConsultaParam(LocalDate data, long idDentista) {
        List<Object[]> liOb = consultaRepository.getConsultaParam(data, idDentista);
        List<ConsultaListaDTO> lista = new ArrayList<>();
        for (Object[] ob : liOb) {
            ConsultaListaDTO c = new ConsultaListaDTO( (Long)ob[0], 
            ((java.sql.Date) ob[1]).toLocalDate(), 
            ((java.sql.Time) ob[2]).toLocalTime(), 
            (String) ob[3], 
            (String) ob[4], 
            (String) ob[5], 
            (Double) ob[6]);
            lista.add(c);
        }
        return lista;
    }

}
