package com.lucfritzke.bludent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Paciente;
import com.lucfritzke.bludent.exceptions.NotFoundException;
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

    @Override
    public void delete(Long id) {
        try {
            super.delete(id);
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Paciente esta sendo referenciado em outra entidade");
        }
        super.delete(id);
    }

}
