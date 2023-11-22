package com.lucfritzke.bludent.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Procedimento;
import com.lucfritzke.bludent.repositories.ProcedimentoRepository;

@Service
public class ProcedimentoService extends ServiceAbstract<Procedimento>{
    
    @Autowired
    private ProcedimentoRepository repository;
    
    @Override
    public JpaRepository<Procedimento, Long> repository() {
        return this.repository;
    }
    
}
