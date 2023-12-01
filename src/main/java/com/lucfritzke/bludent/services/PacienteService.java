package com.lucfritzke.bludent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Paciente;
import com.lucfritzke.bludent.repositories.PacienteRepository;

@Service
public class PacienteService extends ServiceAbstract<Paciente> {

    @Autowired
    private PacienteRepository repository;

    @Override
    public JpaRepository<Paciente, Long> repository() {
        return this.repository;
    }

    @Override
    public Paciente create(Paciente entity) {
        if (repository.existsByCpf(entity.getCpf())) {
            throw new DataIntegrityViolationException("CPF já cadastrado");
        }
        return super.create(entity);
    }

   

}
