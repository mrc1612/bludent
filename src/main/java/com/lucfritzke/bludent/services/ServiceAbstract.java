package com.lucfritzke.bludent.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucfritzke.bludent.dto.ErroDTO;
import com.lucfritzke.bludent.exceptions.DeleteException;
import com.lucfritzke.bludent.exceptions.NotFoundException;

public abstract class ServiceAbstract<T> {

    public abstract JpaRepository<T, Long> repository();

    public T create(T entity){
        return repository().save(entity);
    }

    public T update(T entity, Long id) {
        Optional<T> e = repository().findById(id);
        if (e.isPresent()) {
            return repository().save(entity);
        }
        throw new NotFoundException("Entidade não encontrada");
    }

    public List<T> findAll() {
        return repository().findAll();
    } 

    public T findById(Long id) {

        return repository().findById(id).orElseThrow(() ->  new NotFoundException("Entidade não encontrada"));

    }

    public T findByIdDelete(Long id) {

        return repository().findById(id).orElseThrow(() ->  new DeleteException(new ErroDTO("ERRO", "Entidade não encontrada")));

    }

    public void delete(Long id) {
       repository().delete(
                repository().findById(id)
                        .orElseThrow(() -> new DeleteException(new ErroDTO("ERRO", "Entidade não encontrada"))));
    }
}
