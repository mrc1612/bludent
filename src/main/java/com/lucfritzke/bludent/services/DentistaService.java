package com.lucfritzke.bludent.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.lucfritzke.bludent.domain.Dentista;
import com.lucfritzke.bludent.domain.ProcedimentoDentista;
import com.lucfritzke.bludent.exceptions.NotFoundException;
import com.lucfritzke.bludent.repositories.DentistaRepository;


@Service
public class DentistaService extends ServiceAbstract<Dentista> {

    @Autowired
    private DentistaRepository repository;

    @Autowired
    private ProcedimentoDentistaService pdservice;

    @Override
    public JpaRepository<Dentista, Long> repository() {
        return this.repository;
    }

    @Override
    public void delete(Long id) {
        
        Dentista d = this.findById(id);
        List<ProcedimentoDentista> pd =  pdservice.findDentistaById(d);
        if(!pd.isEmpty()){
            throw new DataIntegrityViolationException("Entida esta sendo referenciada por outra entidade. Não pode ser excluida");
        }
        super.delete(id);
    }

    @Override
    public Dentista findById(Long id) {
            return repository().findById(id).orElseThrow(() ->  new NotFoundException("Dentista não encontrado"));
    }

    

    
    

}
