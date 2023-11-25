package com.lucfritzke.bludent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Consulta;
import com.lucfritzke.bludent.domain.ProcedimentoDentistaId;
import com.lucfritzke.bludent.dto.ConsultaDTO;
import com.lucfritzke.bludent.repositories.ConsultaRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;


    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ProcedimentoDentistaService procedimentoDentistaService;




    public Consulta create(ConsultaDTO c){
        
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
    
}
