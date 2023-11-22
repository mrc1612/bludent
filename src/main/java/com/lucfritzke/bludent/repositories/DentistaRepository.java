package com.lucfritzke.bludent.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucfritzke.bludent.domain.Dentista;

public interface DentistaRepository extends JpaRepository<Dentista, Long>{

    List<Dentista> findByNomeContaining(String nome);
    
}
